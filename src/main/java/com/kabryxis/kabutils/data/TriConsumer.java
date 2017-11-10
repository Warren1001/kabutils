package com.kabryxis.kabutils.data;

import org.apache.commons.lang3.Validate;

public interface TriConsumer<A, B, C> {
	
	public void accept(final A a, final B b, final C c);
	
	public default TriConsumer<A, B, C> andThen(final TriConsumer<? super A, ? super B, ? super C> after) {
		Validate.notNull(after);
		return (a, b, c) -> {
			accept(a, b, c);
			after.accept(a, b, c);
		};
	}
}
