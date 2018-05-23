package com.kabryxis.kabutils.data;

@FunctionalInterface
public interface BiSupplier<R, T, U> {
	
	R get(T t, U u);
	
}
