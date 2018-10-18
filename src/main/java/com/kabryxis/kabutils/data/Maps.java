package com.kabryxis.kabutils.data;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Maps {
	
	@SuppressWarnings("unchecked")
	public static <A, B, C, D> Map<C, D> tryCast(Map<A, B> map) {
		try {
			return (Map<C, D>)map;
		} catch(ClassCastException ignore) {
			return null;
		}
	}
	
	public static <A, B, C, D> Map<C, D> convert(Map<A, B> map, Function<A, C> keyConverter, Function<B, D> valueConverter) {
		Map<C, D> newMap = tryCast(map);
		if(newMap != null) return newMap;
		newMap = new HashMap<>(map.size());
		for(Map.Entry<A, B> entry : map.entrySet()) {
			newMap.put(keyConverter.apply(entry.getKey()), valueConverter.apply(entry.getValue()));
		}
		return newMap;
	}
	
	public static <K, V> Map<K, V> getFromCache(CacheBuilder<Object, Object> builder) {
		Cache<K, V> cache = builder.build();
		return cache.asMap();
	}
	
}
