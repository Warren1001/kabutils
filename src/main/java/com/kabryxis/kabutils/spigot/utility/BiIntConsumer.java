package com.kabryxis.kabutils.spigot.utility;

@FunctionalInterface
public interface BiIntConsumer<T> {
	
	void accept(int i, T value);
	
}
