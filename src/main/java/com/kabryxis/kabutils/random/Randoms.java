package com.kabryxis.kabutils.random;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Randoms {
	
	private final static Random random = new Random();
	
	public static <T> T getRandom(List<T> list) {
		return list.get(random.nextInt(list.size()));
	}
	
	public static <T> T getRandom(List<T> list, T[] alreadyChosen) {
		List<T> reducedList = new ArrayList<>(list);
		for(T remove : alreadyChosen) {
			reducedList.remove(remove);
		}
		return getRandom(reducedList);
	}
	
	public static <T> List<T> getRandomAmount(List<T> list, int amount) {
		List<T> newList = new ArrayList<>(amount);
		for(int i = 0; i < amount; i++) {
			newList.add(getRandom(list));
		}
		return newList;
	}
	
	public static <T> List<T> getRandomAmount(List<T> list, int amount, int noRepeat) {
		List<T> copiedList = new ArrayList<>(list);
		List<T> newList = new ArrayList<>(amount);
		List<T> lastChosen = new ArrayList<>(noRepeat);
		for(int i = 0; i < amount; i++) {
			if(!lastChosen.isEmpty()) {
				T readd = lastChosen.remove(0);
				copiedList.add(readd);
			}
			T selection = getRandom(copiedList);
			copiedList.remove(selection);
			lastChosen.add(selection);
			newList.add(selection);
		}
		return newList;
	}
	
	public static <T> T getRandom(T[] array) {
		return array.length == 1 ? array[0] : array[random.nextInt(array.length)];
	}
	
}
