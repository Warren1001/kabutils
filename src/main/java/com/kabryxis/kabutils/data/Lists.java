package com.kabryxis.kabutils.data;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Lists {
	
	public static <T> List<T> merge(List<List<T>> lists) {
		List<T> newList = new ArrayList<>();
		for(List<T> list : lists) {
			newList.addAll(list);
		}
		return newList;
	}
	
	public static <T> List<T> convert(List<?> list, Class<T> clazz) {
		if(list == null) return null;
		List<T> newList = tryCast(list, clazz);
		if(newList != null) return newList;
		if(list.isEmpty()) return new ArrayList<>();
		newList = new ArrayList<>(list.size());
		for(Object obj : list) {
			if(clazz.isInstance(obj)) newList.add(clazz.cast(obj));
			else return null;
		}
		return newList;
	}
	
	public static <T> List<T> convert(List<?> list, Function<Object, T> converter) {
		if(list == null) return null;
		if(list.isEmpty()) return new ArrayList<>();
		return list.stream().map(converter).collect(Collectors.toList());
	}
	
	public static <T> List<T> convert(List<?> list, Class<T> clazz, Function<Object, T> converter) {
		if(list == null) return null;
		List<T> newList = tryCast(list, clazz);
		if(newList != null) return newList;
		if(list.isEmpty()) return new ArrayList<>();
		return list.stream().map(obj -> clazz.isInstance(obj) ? clazz.cast(obj) : converter.apply(obj)).collect(Collectors.toList());
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> tryCast(List<?> list, Class<T> clazz) {
		return list.stream().allMatch(clazz::isInstance) ? (List<T>)list : null;
	}
	
}
