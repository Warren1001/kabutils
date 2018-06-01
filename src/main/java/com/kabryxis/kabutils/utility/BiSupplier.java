package com.kabryxis.kabutils.utility;

@FunctionalInterface
public interface BiSupplier<R, T, U> {
	
	R get(T t, U u);
	
}
