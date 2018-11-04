package com.kabryxis.kabutils.data;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.Validate;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Maps {
	
	public static <A, B, C, D> Map<C, D> convert(Map<A, B> map, Function<A, C> keyConverter, Function<B, D> valueConverter) {
		Map<C, D> newMap = new HashMap<>(map.size());
		map.forEach((key, value) -> newMap.put(keyConverter.apply(key), valueConverter.apply(value)));
		return newMap;
	}
	
	public static <K, V> Map<K, V> getFromCache(CacheBuilder<Object, Object> builder) {
		Cache<K, V> cache = builder.build();
		return cache.asMap();
	}
	
	public static <K, V> Map<K, V> newHashMapFromElements(Function<Object, K> keyConverter, Function<Object, V> valueConverter, Object... elements) {
		Validate.notNull(elements, "elements cannot be null");
		Validate.isTrue(elements.length % 2 == 0 && elements.length > 0, "must have an even number of elements");
		Map<K, V> map = new HashMap<>(elements.length / 2);
		for(int i = 0; i < elements.length; i += 2) {
			map.put(keyConverter.apply(elements[i]), valueConverter.apply(elements[i + 1]));
		}
		return map;
	}
	
}
