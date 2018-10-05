package com.kabryxis.kabutils.spigot.version.wrapper;

import com.kabryxis.kabutils.spigot.version.Version;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class WrapperFactory {
	
	private static final Map<Class<?>, Supplier<?>> cachedEmptySuppliers = new HashMap<>();
	private static final Map<Class<?>, Map<Class<?>, Function<Object, ?>>> cachedSuppliers = new HashMap<>();
	
	@SuppressWarnings("unchecked")
	public static <T extends Wrappable> Supplier<? extends T> getSupplier(Class<T> clazz) {
		return (Supplier<? extends T>)cachedEmptySuppliers.computeIfAbsent(clazz, wrapperClass -> () -> {
			try {
				return (T)getImplementationClass(clazz).getConstructor().newInstance();
			} catch(InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				throw new RuntimeException(e);
			}
		});
	}
	
	public static <T extends Wrappable> T get(Class<T> clazz) {
		return getSupplier(clazz).get();
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Wrappable> Function<Object, ? extends T> getSupplier(Class<T> clazz, Class<?> paramClass) {
		return (Function<Object, ? extends T>)cachedSuppliers.computeIfAbsent(clazz, wrapperClass -> new HashMap<>()).computeIfAbsent(paramClass, ignore -> obj -> {
			try {
				return (T)getImplementationClass(clazz).getConstructor(paramClass).newInstance(obj);
			} catch(InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				throw new RuntimeException(e);
			}
		});
	}
	
	public static <T extends Wrappable, U> T get(Class<T> clazz, Class<U> paramClass, U obj) {
		return getSupplier(clazz, paramClass).apply(obj);
	}
	
	private static Class<?> getImplementationClass(Class<?> clazz) {
		try {
			return Class.forName(clazz.getPackage().getName() + ".impl." + clazz.getSimpleName() + Version.VERSION.getImplementationNamespace());
		} catch(ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
}
