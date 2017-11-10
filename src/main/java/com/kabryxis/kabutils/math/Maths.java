package com.kabryxis.kabutils.math;

import java.util.Collection;
import java.util.function.Function;

public class Maths {
	
	public static int ceil(double d) {
		return (int)Math.ceil(d);
	}
	
	public static <T> int biggest(T[] objects, Function<T, Integer> action, int base) {
		int biggest = base;
		for(T object : objects) {
			int value = action.apply(object).intValue();
			if(value > biggest) biggest = value;
		}
		return biggest;
	}
	
	public static <T> int biggest(T[] objects, Function<T, Integer> action) {
		return biggest(objects, action, 0);
	}
	
	public static <T> int biggest(Collection<T> objects, Function<T, Integer> action, int base) {
		int biggest = base;
		for(T object : objects) {
			int value = action.apply(object).intValue();
			if(value > biggest) biggest = value;
		}
		return biggest;
	}
	
	public static <T> int biggest(Collection<T> objects, Function<T, Integer> action) {
		return biggest(objects, action, 0);
	}
	
}
