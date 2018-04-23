package com.kabryxis.kabutils.random;

import java.util.*;

public class Randoms {
	
	private final static Random random = new Random();
	
	public static <T> T getRandom(List<T> list) {
		return list.get(random.nextInt(list.size()));
	}
	
	public static <T> T getRandomAndRemove(List<T> list) {
		return list.remove(random.nextInt(list.size()));
	}
	
	public static <T> List<T> getRandomAmount(List<T> list, int amount) {
		List<T> newList = new ArrayList<>(amount);
		for(int i = 0; i < amount; i++) {
			newList.add(getRandom(list));
		}
		return newList;
	}
	
	public static <T> T getRandom(T[] array) {
		return array.length == 1 ? array[0] : array[random.nextInt(array.length)];
	}
	
	public static <T> T getRandom(Collection<T> collection) {
		Iterator<T> iter = collection.iterator();
		for(int i = 0; i < random.nextInt(collection.size()); i++) {
			iter.next();
		}
		return iter.next();
	}
	
}
