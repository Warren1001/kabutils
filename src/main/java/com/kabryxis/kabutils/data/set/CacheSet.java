package com.kabryxis.kabutils.data.set;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;

public class CacheSet<E> extends AbstractSet<E> implements Set<E>, Cloneable {
	
	private static final Object PRESENT = new Object();
	
	private final CacheBuilder<Object, Object> builder;
	
	private transient Cache<E, Object> cache;
	
	public CacheSet(CacheBuilder<Object, Object> builder) {
		this.builder = builder;
		this.cache = builder.build();
	}
	
	@Override
	public Iterator<E> iterator() {
		return cache.asMap().keySet().iterator();
	}
	
	@Override
	public int size() {
		return (int)cache.size();
	}
	
	@Override
	public boolean contains(Object o) {
		return cache.getIfPresent(o) != null;
	}
	
	@Override
	public boolean add(E e) {
		if(contains(e)) return false;
		cache.put(e, PRESENT);
		return true;
	}
	
	@Override
	public boolean remove(Object o) {
		boolean exists = contains(o);
		cache.invalidate(o);
		return exists;
	}
	
	@Override
	public void clear() {
		cache.invalidateAll();
	}
	
	@Override
	public CacheSet<E> clone() {
		CacheSet<E> clone = new CacheSet<>(builder);
		clone.cache.putAll(cache.asMap());
		return clone;
	}
	
}
