package com.kabryxis.kabutils.random.weighted.conditional;

import java.util.List;
import java.util.stream.Collectors;

public class SelfConditionalWeightedRandomArrayList<E extends ConditionalWeighted<Object>> extends ConditionalWeightedRandomArrayList<E> {
	
	public SelfConditionalWeightedRandomArrayList() {
		super();
	}
	
	public SelfConditionalWeightedRandomArrayList(int noRepeat) {
		super(noRepeat);
	}
	
	public SelfConditionalWeightedRandomArrayList(int noRepeat, int capacity) {
		super(noRepeat, capacity);
	}
	
	public E random() { // TODO probably needs some revising
		if(currNoRepeat == -1) throw new IllegalStateException("Could not find a valid object");
		E value;
		if(currNoRepeat == 0) {
			value = list.get(0);
			if(!value.test(value)) throw new IllegalStateException("Could not find a valid object");
		}
		else {
			List<E> testedList = list.stream().filter(v -> v.test(v)).collect(Collectors.toList());
			int size = testedList.size();
			if(size == 0) {
				testedList = used.stream().filter(v -> v.test(v)).collect(Collectors.toList());
				size = testedList.size();
			}
			if(size == 0) throw new IllegalStateException("Could not find a valid object");
			value = size == 1 ? testedList.get(0) : testedList.get(getWeightedIndex(testedList));
		}
		if(currNoRepeat != 0) {
			list.remove(value);
			used.add(value);
		}
		if(used.size() == currNoRepeat + 1) list.add(used.remove(0));
		return value;
	}
	
}
