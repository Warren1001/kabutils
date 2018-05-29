package com.kabryxis.kabutils.random.weighted.conditional;

import com.kabryxis.kabutils.random.weighted.Weighted;

import java.util.function.Predicate;

public interface ConditionalWeighted<E> extends Predicate<E>, Weighted {}
