package com.kabryxis.kabutils.random;

import java.util.*;

public class Randoms {
	
	public static <T> T getRandom(List<T> list) {
		return list.get(new Random().nextInt(list.size()));
	}
	
	public static <T> T getRandom(List<T> list, T noRepeat) {
		T choice = null;
		while(choice == null || noRepeat.equals(choice)) {
			choice = getRandom(list);
		}
		return choice;
	}
	
	public static <T> T getRandomAndRemove(List<T> list) {
		return list.remove(getRandomIndex(list));
	}
	
	public static <T> List<T> getRandomAmount(List<T> list, int amount) {
		List<T> newList = new ArrayList<>(amount);
		for(int i = 0; i < amount; i++) {
			newList.add(getRandom(list));
		}
		return newList;
	}
	
	public static <T> int getRandomIndex(List<T> list) {
		return list.size() == 1 ? 0 : new Random().nextInt(list.size());
	}
	
	public static <T> int getRandomIndex(T[] array) {
		return array.length == 1 ? 0 : new Random().nextInt(array.length);
	}
	
	public static <T> int getRandomIndex(T[] array, T noRepeat) {
		int index = -1;
		while(index == -1 || noRepeat.equals(array[index])) {
			index = getRandomIndex(array);
		}
		return index;
	}
	
	public static int getRandomIndex(Object[] array, int noRepeat) {
		int index = noRepeat;
		while(index == noRepeat) {
			index = getRandomIndex(array);
		}
		return index;
	}
	
	public static <T> T getRandom(T[] array) {
		return array[getRandomIndex(array)];
	}
	
	public static <T> T getRandom(T[] array, T noRepeat) {
		T choice = null;
		while(choice == null || noRepeat.equals(choice)) {
			choice = getRandom(array);
		}
		return choice;
	}
	
	public static <T> T getRandom(Collection<T> collection) {
		Iterator<T> iter = collection.iterator();
		for(int i = 0; i < new Random().nextInt(collection.size()); i++) {
			iter.next();
		}
		return iter.next();
	}
	
}
