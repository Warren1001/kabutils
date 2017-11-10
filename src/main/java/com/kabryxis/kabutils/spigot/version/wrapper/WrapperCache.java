package com.kabryxis.kabutils.spigot.version.wrapper;

import java.util.HashMap;
import java.util.Map;

import com.kabryxis.kabutils.cache.Cache;
import com.kabryxis.kabutils.spigot.version.UnsupportedVersionException;
import com.kabryxis.kabutils.spigot.version.Version;

public class WrapperCache {
	
	private final static Map<Class<? extends Wrappable<?>>, Class<? extends Wrappable<?>>> implementations = new HashMap<>();
	
	public static <T extends Wrappable<?>> T get(Class<T> clazz) {
		Class<? extends Wrappable<?>> implementation = implementations.get(clazz);
		if(implementation == null) {
			try {
				implementation = (Class<? extends Wrappable<?>>)Class.forName(clazz.getPackage().getName() + ".impl." + clazz.getSimpleName() + Version.VERSION.getImplementationNamespace());
			}
			catch(ClassNotFoundException e) {
				throw new UnsupportedVersionException(clazz);
			}
			implementations.put(clazz, implementation);
		}
		return (T)Cache.get(implementation);
	}
	
}
