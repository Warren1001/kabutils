package com.kabryxis.kabutils.utility;

@FunctionalInterface
public interface BiIntConsumer<T> {
	
	void accept(int i, T value);
	
}
