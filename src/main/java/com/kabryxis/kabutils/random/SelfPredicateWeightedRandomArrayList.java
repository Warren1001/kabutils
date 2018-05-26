package com.kabryxis.kabutils.random;

import java.util.List;
import java.util.stream.Collectors;

public class SelfPredicateWeightedRandomArrayList<E extends PredicateWeighted<Object>> extends PredicateWeightedRandomArrayList<E> {
	
	public SelfPredicateWeightedRandomArrayList() {
		super();
	}
	
	public SelfPredicateWeightedRandomArrayList(int noRepeat) {
		super(noRepeat);
	}
	
	public SelfPredicateWeightedRandomArrayList(int noRepeat, int capacity) {
		super(noRepeat, capacity);
	}
	
	public E random() { // TODO probably needs some revising
		if(currNoRepeat == -1) return null;
		E value;
		if(currNoRepeat == 0) {
			value = list.get(0);
			if(!value.test(value)) return null;
		}
		else {
			List<E> testedList = list.stream().filter(v -> v.test(v)).collect(Collectors.toList());
			int size = testedList.size();
			if(size == 0) return null;
			if(size == 1) value = testedList.get(0);
			else value = testedList.get(getWeightedIndex(testedList));
		}
		if(currNoRepeat != 0) {
			list.remove(value);
			used.add(value);
		}
		if(used.size() == currNoRepeat + 1) list.add(used.remove(0));
		return value;
	}
	
}
