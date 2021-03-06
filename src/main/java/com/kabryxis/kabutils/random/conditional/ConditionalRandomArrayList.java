package com.kabryxis.kabutils.random.conditional;

import com.kabryxis.kabutils.random.RandomArrayList;
import com.kabryxis.kabutils.random.Randoms;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConditionalRandomArrayList<E> extends RandomArrayList<E> {
	
	public ConditionalRandomArrayList() {
		super();
	}
	
	public ConditionalRandomArrayList(int noRepeat) {
		super(noRepeat);
	}
	
	public ConditionalRandomArrayList(int noRepeat, int capacity) {
		super(noRepeat, capacity);
	}
	
	public E random(Predicate<Object>... predicates) {
		if(currNoRepeat == -1) throw new IllegalStateException("Could not find a valid custom");
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
			if(!check) throw new IllegalStateException("Could not find a valid custom");
		}
		else {
			Stream<E> stream = list.stream();
			for(Predicate<Object> predicate : predicates) {
				stream = stream.filter(predicate);
			}
			List<E> testedList = stream.collect(Collectors.toList());
			int size = testedList.size();
			if(size == 0) {
				stream = used.stream();
				for(Predicate<Object> predicate : predicates) {
					stream = stream.filter(predicate);
				}
				testedList = stream.collect(Collectors.toList());
				size = testedList.size();
			}
			if(size == 0) throw new IllegalStateException("Could not find a valid custom");
			value = size == 1 ? testedList.get(0) : Randoms.getRandom(testedList);
		}
		if(currNoRepeat != 0) {
			list.remove(value);
			used.add(value);
		}
		if(used.size() == currNoRepeat + 1) list.add(used.remove(0));
		return value;
	}
	
}
