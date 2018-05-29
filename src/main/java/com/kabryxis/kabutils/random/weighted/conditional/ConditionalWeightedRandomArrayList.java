package com.kabryxis.kabutils.random.weighted.conditional;

import com.kabryxis.kabutils.random.weighted.Weighted;
import com.kabryxis.kabutils.random.weighted.WeightedRandomArrayList;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConditionalWeightedRandomArrayList<E extends Weighted> extends WeightedRandomArrayList<E> {
	
	public ConditionalWeightedRandomArrayList() {
		super();
	}
	
	public ConditionalWeightedRandomArrayList(int noRepeat) {
		super(noRepeat);
	}
	
	public ConditionalWeightedRandomArrayList(int noRepeat, int capacity) {
		super(noRepeat, capacity);
	}
	
	public E random(Predicate<Object>... predicates) {
		if(currNoRepeat == -1) return null;
		E value;
		if(currNoRepeat == 0) {
			value = list.get(0);
			boolean check = true;
			for(Predicate<Object> predicate : predicates) {
				if(!predicate.test(value)) {
					check = false;
					break;
				}
			}
			if(!check) return null;
		}
		else {
			Stream<E> stream = list.stream();
			for(Predicate<Object> predicate : predicates) {
				stream = stream.filter(predicate);
			}
			List<E> testedList = stream.collect(Collectors.toList());
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
