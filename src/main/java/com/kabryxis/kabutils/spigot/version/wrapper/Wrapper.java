package com.kabryxis.kabutils.spigot.version.wrapper;

import com.kabryxis.kabutils.cache.Cache;
import com.kabryxis.kabutils.spigot.version.Wrappable;

public abstract class Wrapper<T> implements Wrappable {
	
	private T object;
	
	public void set(T object) {
		this.object = object;
	}
	
	public T get() {
		return object;
	}
	
	public void setHandle(Object object) {
		set((T)object);
	}
	
	@Override
	public void clear() {
		object = null;
	}
	
	@Override
	public void cache() {
		clear();
		Cache.cache(this);
	}
	
}
