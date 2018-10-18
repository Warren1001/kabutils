package com.kabryxis.kabutils.data;

import com.avaje.ebean.validation.NotNull;

public class Objects {
	
	@NotNull
	@SafeVarargs
	public static <T> T getFirstNonnull(T... objs) {
		for(T obj : objs) {
			if(obj != null) return obj;
		}
		throw new NullPointerException("All of the objects in the provided array are null");
	}
	
}
