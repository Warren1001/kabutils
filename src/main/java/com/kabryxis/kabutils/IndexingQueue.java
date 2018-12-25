package com.kabryxis.kabutils;

import org.apache.commons.lang3.Validate;

import java.util.AbstractList;
import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public class IndexingQueue<E> extends AbstractList<E> {
	
	final Object[] elements;
	final Consumer[] actions;
	
	public IndexingQueue(int capacity) {
		Validate.isTrue(capacity > 0, "capacity must be greater than zero");
		elements = new Object[capacity];
		actions = new Consumer[capacity + 1];
	}
	
	public IndexingQueue(int capacity, Consumer<E> removeAction) {
		this(capacity);
		actions[actions.length - 1] = removeAction;
	}
	
	public void setIndexAction(int index, Consumer<E> action) {
		actions[checkIndex(index)] = action;
	}
	
	@Override
	public void add(int index, E element) {
		add(element);
	}
	
	@Override
	public boolean add(E element) {
		Validate.notNull(element, "element cannot be null");
		E last = (E)elements[elements.length - 1];
		if(last != null) {
			Consumer action = actions[elements.length];
			if(action != null) action.accept(last);
		}
		for(int i = elements.length - 2; i >= 0; i--) {
			E e = (E)elements[i];
			elements[i + 1] = e;
			if(e != null) {
				Consumer action = actions[i + 1];
				if(action != null) action.accept(e);
			}
		}
		elements[0] = element;
		Consumer action = actions[0];
		if(action != null) action.accept(element);
		return true;
	}
	
	@Override
	public E remove(int index) {
		E element = get(index);
		if(element != null) {
			Consumer action = actions[elements.length];
			if(action != null) action.accept(element);
		}
		return element;
	}
	
	@Override
	public E get(int index) {
		return (E)elements[checkIndex(index)];
	}
	
	@Override
	public int size() {
		int index = -1;
		for(int i = elements.length - 1; i >= 0; i--) {
			if(elements[i] != null) {
				index = i;
				break;
			}
		}
		return index + 1;
	}
	
	@Override
	public void clear() {
		Consumer action = actions[elements.length];
		for(int i = 0; i < elements.length; i++) {
			E element = (E)elements[i];
			elements[i] = null;
			if(element != null && action != null) action.accept(element);
		}
	}
	
	private int checkIndex(int index) {
		if(index < 0 || index >= elements.length) throw new ArrayIndexOutOfBoundsException(String.format("Index: %s, Capacity: %s", index, elements.length));
		return index;
	}
	
}
