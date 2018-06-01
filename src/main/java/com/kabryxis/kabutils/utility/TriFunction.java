package com.kabryxis.kabutils.utility;

import org.apache.commons.lang3.Validate;

import java.util.function.Function;

@FunctionalInterface
public interface TriFunction<A, B, C, R> {
	
	R apply(A a, B b, C c);
	
	default <V> TriFunction<A, B, C, V> andThen(Function<? super R, ? extends V> after) {
		Validate.notNull(after);
		return (A a, B b, C c) -> after.apply(apply(a, b, c));
	}
}
