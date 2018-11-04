package com.kabryxis.kabutils.data;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Arrays {
	
	public static <T> boolean contains(T[] array, T obj) {
		if(obj == null) {
			for(T t : array) {
				if(t == null) return true;
			}
		}
		else {
			for(T t : array) {
				if(obj.equals(t)) return true;
			}
		}
		return false;
	}
	
	public static <T> T[] splitArray(T[] originalArray, int start) {
		return java.util.Arrays.copyOfRange(originalArray, start, originalArray.length - 1);
	}
	
	public static <T> T computeIfAbsent(T[] array, int index, Supplier<T> supplier) {
		T obj = array[index];
		if(obj == null) array[index] = (obj = supplier.get());
		return obj;
	}
	
	public static <T> void forEach(T[] array, Consumer<? super T> action) {
		for(T t : array) {
			action.accept(t);
		}
	}
	
}
