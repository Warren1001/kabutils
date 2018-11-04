package com.kabryxis.kabutils.spigot.version.wrapper;

import com.kabryxis.kabutils.spigot.version.Version;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class WrapperFactory {
	
	private static final Map<Class<?>, Supplier<?>> cachedEmptySuppliers = new HashMap<>();
	private static final Map<Class<?>, Map<Class<?>[], Function<Object[], ?>>> cachedSuppliers = new HashMap<>();
	
	@SuppressWarnings("unchecked")
	public static <T> Supplier<? extends T> getSupplier(Class<T> clazz) {
		return (Supplier<? extends T>)cachedEmptySuppliers.computeIfAbsent(clazz, wrapperClass -> () -> {
			try {
				return (T)getImplementationClass(clazz).getConstructor().newInstance();
			} catch(InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				throw new RuntimeException(e);
			}
		});
	}
	
	public static <T> T get(Class<T> clazz) {
		return getSupplier(clazz).get();
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Function<Object[], ? extends T> getSupplier(Class<T> clazz, Class<?>[] paramClasses) {
		return (Function<Object[], ? extends T>)cachedSuppliers.computeIfAbsent(clazz, wrapperClass -> new HashMap<>()).computeIfAbsent(paramClasses, ignore -> obj -> {
			try {
				return (T)getImplementationClass(clazz).getConstructor(paramClasses).newInstance(obj);
			} catch(InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				throw new RuntimeException(e);
			}
		});
	}
	
	public static <T> T get(Class<T> clazz, Class<?>[] paramClasses, Object[] obj) {
		return getSupplier(clazz, paramClasses).apply(obj);
	}
	
	public static <T> Class<? extends T> getImplementationClass(Class<T> clazz) {
		try {
			return Class.forName(clazz.getPackage().getName() + ".impl." + clazz.getSimpleName() + Version.VERSION.getImplementationNamespace()).asSubclass(clazz);
		} catch(ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Object getHandle(Object obj) {
		Class<?> clazz = obj.getClass();
		Method method;
		try {
			method = clazz.getDeclaredMethod("getHandle");
		} catch(NoSuchMethodException e) {
			throw new RuntimeException(String.format("%s does not have a getHandle method", clazz.getName()), e);
		}
		try {
			return method.invoke(obj);
		} catch(IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static boolean isInstance(Object obj, Class<?> clazz) {
		return clazz.isInstance(getHandle(obj));
	}
	
	public static <T> T castHandle(Object obj, Class<T> clazz) {
		Object handle = getHandle(obj);
		return clazz.isInstance(handle) ? clazz.cast(handle) : null;
	}
	
}
