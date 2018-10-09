package com.kabryxis.kabutils.data;

import java.util.function.Supplier;

public class Arrays {
	
	public static <T> boolean containsInstance(T[] array, T obj) {
		for(T t : array) {
			if(t == obj) return true;
		}
		return false;
	}
	
	public static boolean containsInt(int[] array, int i) {
		for(int a : array) {
			if(a == i) return true;
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
	
}
