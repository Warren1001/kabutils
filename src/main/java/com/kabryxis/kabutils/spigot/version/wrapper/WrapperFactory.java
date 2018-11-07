package com.kabryxis.kabutils.spigot.version.wrapper;

import com.kabryxis.kabutils.data.VarargsFunction;
import com.kabryxis.kabutils.spigot.version.Version;
import org.apache.commons.lang3.Validate;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class WrapperFactory {
	
	private static final Map<Class<?>, Supplier<?>> cachedEmptySuppliers = new HashMap<>();
	private static final Map<Class<?>, Map<Class<?>[], VarargsFunction<?>>> cachedSuppliers = new HashMap<>();
	private static final Map<Class<?>, Function<Object, Object>> cachedGetHandleMethods = new HashMap<>();
	private static final Map<Class<?>, Class<?>> cachedImplementationClasses = new HashMap<>();
	
	@SuppressWarnings("unchecked")
	public static <T> Supplier<T> getSupplier(Class<T> clazz) {
		return (Supplier<T>)cachedEmptySuppliers.computeIfAbsent(clazz, wrapperClass -> new Supplier<T>() {
			
			private final Constructor<? extends T> constructor;
			
			{
				try {
					constructor = getImplementationClass(clazz).getConstructor();
				} catch(NoSuchMethodException | SecurityException e) {
					throw new RuntimeException(e);
				}
			}
			
			@Override
			public T get() {
				try {
					return constructor.newInstance();
				} catch(InstantiationException | IllegalAccessException | InvocationTargetException e) {
					throw new RuntimeException(e);
				}
			}
		
		});
	}
	
	public static <T> T get(Class<T> clazz) {
		return getSupplier(clazz).get();
	}
	
	@SuppressWarnings("unchecked")
	public static <T> VarargsFunction<T> getSupplier(Class<T> clazz, Class<?>... paramClasses) {
		return (VarargsFunction<T>)cachedSuppliers.computeIfAbsent(clazz, wrapperClass -> new HashMap<>()).computeIfAbsent(paramClasses, classes -> new VarargsFunction<T>() {
			
			private final Constructor<? extends T> constructor;
			
			{
				try {
					constructor = getImplementationClass(clazz).getConstructor(classes);
				} catch(NoSuchMethodException | SecurityException e) {
					throw new RuntimeException(e);
				}
			}
			
			@Override
			public T apply(Object... objs) {
				try {
					return constructor.newInstance(objs);
				} catch(InstantiationException | IllegalAccessException | InvocationTargetException e) {
					throw new RuntimeException(e);
				}
			}
			
		});
	}
	
	public static <T> T get(Class<T> clazz, Class<?>[] paramClasses, Object... objs) {
		return getSupplier(clazz, fillClasses(paramClasses, objs)).apply(objs);
	}
	
	public static <T> T get(Class<T> clazz, Object... objs) {
		return get(clazz, getClasses(objs), objs);
	}
	
	public static Class<?>[] fillClasses(Class<?>[] classes, Object... objs) {
		for(int i = 0; i < classes.length; i++) {
			if(classes[i] == null && objs[i] != null) classes[i] = objs[i].getClass();
		}
		return classes;
	}
	
	public static Class<?>[] getClasses(Object... objs) {
		return fillClasses(new Class<?>[objs.length], objs);
	}
	
	public static <T> Class<? extends T> getImplementationClass(Class<T> clazz) {
		return cachedImplementationClasses.computeIfAbsent(clazz, localClass -> {
			try {
				Class<?> implClass = Class.forName(localClass.getPackage().getName() + ".impl." + localClass.getSimpleName() + Version.VERSION.getImplementationNamespace());
				Validate.isTrue(localClass.isAssignableFrom(implClass), "%s does not extend or implement %s", implClass.getName(), localClass.getName());
				return implClass;
			} catch(ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}).asSubclass(clazz);
	}
	
	public static Object getHandle(Object obj) {
		return cachedGetHandleMethods.computeIfAbsent(obj.getClass(), clazz -> new Function<Object, Object>() {
			
			private final Method method;
			
			{
				try {
					method = clazz.getDeclaredMethod("getHandle");
				} catch(NoSuchMethodException e) {
					throw new RuntimeException(String.format("%s does not have a getHandle method", clazz.getName()), e);
				}
			}
			
			@Override
			public Object apply(Object o) {
				try {
					return method.invoke(o);
				} catch(IllegalAccessException | InvocationTargetException e) {
					throw new RuntimeException(e);
				}
			}
			
		}).apply(obj);
	}
	
	public static boolean isInstance(Object obj, Class<?> clazz) {
		return clazz.isInstance(getHandle(obj));
	}
	
	public static <T> T castHandle(Object obj, Class<T> clazz) {
		Object handle = getHandle(obj);
		return clazz.isInstance(handle) ? clazz.cast(handle) : null;
	}
	
}
