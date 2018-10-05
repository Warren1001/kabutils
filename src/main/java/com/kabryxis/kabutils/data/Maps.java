package com.kabryxis.kabutils.data;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Maps {
	
	public static <A, B, C, D> Map<C, D> convertMap(Map<A, B> convert, Function<A, C> keyConverter, Function<B, D> valueConverter) {
		Map<C, D> newMap = new HashMap<>(convert.size());
		convert.forEach((a, b) -> newMap.put(keyConverter.apply(a), valueConverter.apply(b)));
		return newMap;
	}
	
}
