package com.kabryxis.kabutils.data;

import org.apache.commons.lang3.Validate;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Arrays {
	
	public static <T> boolean contains(T[] array, T obj) {
		return Stream.of(array).anyMatch(o -> Objects.equals(o, obj));
	}
	
	public static boolean containsInt(int[] array, int i) {
		for(int i1 : array) {
			if(i1 == i) return true;
		}
		return false;
	}
	
	public static <T> T[] splitArray(T[] originalArray, int start) {
		return java.util.Arrays.copyOfRange(originalArray, start, originalArray.length);
	}
	
	public static <T> T computeIfAbsent(T[] array, int index, Supplier<T> supplier) {
		T obj = array[index];
		if(obj == null) array[index] = (obj = supplier.get());
		return obj;
	}
	
	public static <T> void forEach(T[] array, Consumer<? super T> action) {
		Validate.notNull(action, "action cannot be null");
		if(array != null && array.length != 0) {
			for(T t : array) {
				action.accept(t);
			}
		}
	}
	
}
