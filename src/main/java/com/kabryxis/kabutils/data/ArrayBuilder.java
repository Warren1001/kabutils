package com.kabryxis.kabutils.data;

import java.util.HashMap;
import java.util.Map;

public class ArrayBuilder<E> {
	
	private final Map<Integer, E> map = new HashMap<>();
	
	public ArrayBuilder<E> put(int index, E obj) {
		map.put(index, obj);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public E[] build(int capacity) {
		Object[] elements = new Object[capacity];
		map.forEach((index, obj) -> elements[index] = obj);
		return (E[])elements;
	}
	
	@SuppressWarnings("unchecked")
	public E[] build() {
		Object[] elements = new Object[getHighestIndex()];
		map.forEach((index, obj) -> elements[index] = obj);
		return (E[])elements;
	}
	
	private int getHighestIndex() {
		int highest = 0;
		for(Integer i : map.keySet()) {
			if(i > highest) highest = i;
		}
		return highest;
	}
	
}
