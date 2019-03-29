package com.kabryxis.kabutils.data;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Sets {
	
	public static <T> Set<T> newHashSet(T... elements) {
		Set<T> set = new HashSet<>(elements.length);
		Collections.addAll(set, elements);
		return set;
	}
	
}
