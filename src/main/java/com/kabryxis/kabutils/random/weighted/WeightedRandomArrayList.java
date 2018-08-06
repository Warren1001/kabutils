package com.kabryxis.kabutils.random.weighted;

import com.kabryxis.kabutils.random.RandomArrayList;

import java.util.List;
import java.util.Random;

public class WeightedRandomArrayList<E extends Weighted> extends RandomArrayList<E> {
	
	public WeightedRandomArrayList() {
		super();
	}
	
	public WeightedRandomArrayList(int noRepeat) {
		super(noRepeat);
	}
	
	public WeightedRandomArrayList(int noRepeat, int capacity) {
		super(noRepeat, capacity);
	}
	
	public E random() {
		if(currNoRepeat == -1) return null;
		E value = currNoRepeat == 0 ? list.get(0) : list.remove(getWeightedIndex(list));
		if(currNoRepeat != 0) used.add(value);
		if(used.size() == currNoRepeat + 1) list.add(used.remove(0));
		return value;
	}
	
	protected int getWeightedIndex(List<E> values) {
		int totalWeight = 0;
		for(E value : values) {
			totalWeight += value.getWeight();
		}
		int chosenRarity = new Random().nextInt(totalWeight);
		int currentWeight = 0;
		for(int index = 0; index < values.size(); index++) {
			E value = values.get(index);
			currentWeight += value.getWeight();
			if(currentWeight >= chosenRarity) return index;
		}
		return new Random().nextInt(values.size());
	}
	
}
