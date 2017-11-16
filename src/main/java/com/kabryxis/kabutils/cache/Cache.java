package com.kabryxis.kabutils.cache;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Supplier;

public class Cache<T> {
	
	private final static Map<Class<?>, Cache<?>> caches = new ConcurrentHashMap<>();
	
	public static <T> void allocateCache(Class<T> clazz, Queue<T> queue) {
		if(!caches.containsKey(clazz)) caches.put(clazz, new Cache<>(queue, clazz));
	}
	
	public static <T> void allocateCache(Class<T> clazz, Queue<T> queue, Supplier<T> supplier) {
		if(!caches.containsKey(clazz)) caches.put(clazz, new Cache<>(queue, supplier));
	}
	
	public static <T> T get(Class<T> clazz) {
		Cache<?> cache = caches.get(clazz);
		if(cache == null) return get0(clazz);
		return clazz.cast(cache.get());
	}
	
	public static void cache(Object obj) {
		caches.computeIfAbsent(obj.getClass(), Cache::new).add(obj);
	}
	
	public static void clear(Class<?> clazz) {
		Cache<?> cache = caches.get(clazz);
		if(cache != null) cache.clear();
	}
	
	private static <T> T get0(Class<T> clazz) {
		try {
			return clazz.newInstance();
		}
		catch(InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private Queue<T> cache;
	private Supplier<T> supplier;
	
	private Cache(Class<T> clazz) {
		this(new ConcurrentLinkedQueue<>(), clazz);
	}
	
	private Cache(Queue<T> cache, Class<T> clazz) {
		this(cache, () -> {
			try {
				return clazz.newInstance();
			}
			catch(InstantiationException | IllegalAccessException | IllegalArgumentException e) {
				throw new IllegalStateException(e);
			}
		});
	}
	
	private Cache(Queue<T> cache, Supplier<T> supplier) {
		this.cache = cache;
		this.supplier = supplier;
	}
	
	public void add(Object obj) {
		cache.add((T)obj);
	}
	
	public T get() {
		T obj = cache.poll();
		if(obj == null) obj = supplier.get();
		return obj;
	}
	
	public void clear() {
		cache.clear();
	}
	
}
