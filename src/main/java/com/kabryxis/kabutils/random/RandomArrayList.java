package com.kabryxis.kabutils.random;

import java.util.*;
import java.util.function.Consumer;

public class RandomArrayList<E> implements Collection<E> {
	
	protected final List<E> list;
	protected final List<E> used;
	
	protected int noRepeat;
	protected int currNoRepeat;
	
	public RandomArrayList() {
		this(-1, 10);
	}
	
	public RandomArrayList(int noRepeat) {
		this(noRepeat, 10);
	}
	
	public RandomArrayList(int noRepeat, int capacity) {
		this.list = new ArrayList<>(capacity);
		this.used = new ArrayList<>();
		this.noRepeat = noRepeat;
		this.currNoRepeat = 0;
	}
	
	public RandomArrayList(int noRepeat, List<E> list) {
		this.list = new ArrayList<>(list);
		this.used = new ArrayList<>();
		this.noRepeat = noRepeat;
		update();
	}
	
	public int getCurrNoRepeat() {
		return currNoRepeat;
	}
	
	@Override
	public boolean add(E object) {
		boolean value = list.add(object);
		if(value) update();
		return value;
	}
	
	public boolean add(E... objects) {
		boolean value = false;
		for(E object : objects) {
			if(add(object) && !value) value = true;
		}
		if(value) update();
		return value;
	}
	
	@Override
	public boolean addAll(Collection<? extends E> collection) {
		boolean value = list.addAll(collection);
		if(value) update();
		return value;
	}
	
	@Override
	public boolean remove(Object object) {
		boolean value = list.remove(object);
		if(!value) {
			value = used.remove(object);
			if(value) update();
		}
		else update();
		return value;
	}
	
	@Override
	public boolean removeAll(Collection<?> collection) {
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
		if(currNoRepeat == -1) return list.get(new Random().nextInt(list.size()));
		E value = currNoRepeat == 0 ? list.get(0) : list.remove(new Random().nextInt(list.size()));
		if(currNoRepeat != 0) used.add(value);
		if(used.size() == currNoRepeat + 1) list.add(used.remove(0));
		return value;
	}
	
	protected void update() {
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
	
	@Override
	public int size() {
		return list.size() + used.size();
	}
	
	public int remainingSize() {
		return list.size();
	}
	
	@Override
	public boolean isEmpty() {
		return size() == 0;
	}
	
	@Override
	public void clear() {
		list.clear();
		used.clear();
		update();
	}
	
	@Override
	public void forEach(Consumer<? super E> action) {
		list.forEach(action);
		used.forEach(action);
	}
	
	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException("simply because im lazy.");
	}
	
	@Override
	public Object[] toArray() {
		throw new UnsupportedOperationException("simply because im lazy.");
	}
	
	@Override
	public Iterator<E> iterator() {
		throw new UnsupportedOperationException("simply because im lazy.");
	}
	
	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException("simply because im lazy.");
	}
	
	@Override
	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException("simply because im lazy.");
	}
	
	@Override
	public boolean contains(Object o) {
		throw new UnsupportedOperationException("simply because im lazy.");
	}
	
}
