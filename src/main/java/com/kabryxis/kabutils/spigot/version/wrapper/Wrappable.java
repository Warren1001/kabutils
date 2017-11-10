package com.kabryxis.kabutils.spigot.version.wrapper;

import com.kabryxis.kabutils.cache.Cache;

public abstract class Wrappable<T> {
	
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
	
	/*public Object getHandle() {
		return object;
	}*/
	
	public void clear() {
		object = null;
	}
	
	public void cache() {
		clear();
		Cache.cache(this);
	}
	
}
