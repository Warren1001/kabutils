package com.kabryxis.kabutils.random;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class RandomArrayList<E> {
	
	private final Random random;
	private final List<E> list;
	private final List<E> used;
	
	private int noRepeat;
	private int currNoRepeat;
	
	public RandomArrayList() {
		this(-1, 10);
	}
	
	public RandomArrayList(int noRepeat) {
		this(noRepeat, 10);
	}
	
	public RandomArrayList(int noRepeat, int capacity) {
		this.random = new Random();
		this.list = new ArrayList<>(capacity);
		this.used = new ArrayList<>();
		this.noRepeat = noRepeat;
		this.currNoRepeat = 0;
	}
	
	public boolean add(E object) {
		boolean value = list.add(object);
		if(value) update();
		return value;
	}
	
	public boolean add(E[] objects) {
		boolean value = true;
		for(E object : objects) {
			if(!add(object) && value) value = false;
		}
		return value;
	}
	
	public boolean addAll(Collection<? extends E> collection) {
		boolean value = list.addAll(collection);
		if(value) update();
		return value;
	}
	
	public boolean remove(E object) {
		boolean value = list.remove(object);
		if(!value) {
			value = used.remove(object);
			if(value) update();
		}
		else update();
		return value;
	}
	
	public boolean removeAll(Collection<E> collection) {
		boolean value = list.removeAll(collection);
		if(!value) {
			value = used.removeAll(collection);
			if(value) update();
		}
		else update();
		return value;
	}
	
	public void setNoRepeat(int noRepeat) {
		this.noRepeat = noRepeat;
		update();
	}
	
	public E random() {
		if(currNoRepeat == -1) throw new IllegalStateException("No values in list to get from");
		E value = currNoRepeat == 0 ? list.get(0) : list.remove(random.nextInt(list.size()));
		if(currNoRepeat != 0) used.add(value);
		if(used.size() == currNoRepeat + 1) list.add(used.remove(0));
		return value;
	}
	
	public List<E> random(int amount) {
		List<E> list = new ArrayList<>(amount);
		for(int i = 0; i < amount; i++) {
			list.add(random());
		}
		return list;
	}
	
	private void update() {
		int size = list.size();
		if(currNoRepeat == size - 1) return;
		currNoRepeat = size <= noRepeat ? size - 1 : noRepeat;
		if(currNoRepeat < 1) {
			if(!used.isEmpty()) {
				list.addAll(used);
				used.clear();
			}
		}
		else {
			int over = used.size() - currNoRepeat + 1;
			if(over > 0) {
				for(; over > 0; over--) {
					E e = used.remove(0);
					list.add(e);
				}
			}
		}
	}
	
}
