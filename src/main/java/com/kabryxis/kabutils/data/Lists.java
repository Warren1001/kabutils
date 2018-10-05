package com.kabryxis.kabutils.data;

import java.util.ArrayList;
import java.util.List;

public class Lists {
	
	public static <T> List<T> merge(List<List<T>> lists) {
		List<T> firstList = new ArrayList<>();
		for(List<T> list : lists) {
			firstList.addAll(list);
		}
		return firstList;
	}
	
	public static <T> List<T> softCopy(List<T> list) {
		List<T> newList = new ArrayList<>(list.size());
		newList.addAll(list);
		return newList;
	}
	
}
