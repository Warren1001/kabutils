package com.kabryxis.kabutils;

@FunctionalInterface
public interface BiIntConsumer<T> {
	
	void accept(int i, T value);
	
}
