package com.kabryxis.kabutils;

import javax.annotation.Nonnull;
import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public class AutoRemovingQueue<E extends AutoRemovable> extends AbstractQueue<E> implements Queue<E> {
	
	private final Object[] elements;
	
	private int tailIndex = 0;
	private boolean silent = false;
	
	public AutoRemovingQueue(int capacity) {
		elements = new Object[capacity];
	}
	
	@Override
	@Nonnull
	public Iterator<E> iterator() {
		return new AutoRemovingIterator();
	}
	
	@Override
	public int size() {
		return tailIndex;
	}
	
	@Override
	public boolean offer(E e) {
		if(tailIndex == elements.length) poll();
		elements[tailIndex++] = e;
		return true;
	}
	
	@Override
	public E poll() {
		if(isEmpty()) return null;
		E e = peek();
		if(size() > 1) System.arraycopy(elements, 1, elements, 0, tailIndex - 1);
		tailIndex--;
		elements[tailIndex] = null;
		if(e != null) e.remove(silent);
		return e;
	}
	
	@Override
	public E peek() {
		return get(0);
	}
	
	@Override
	public void clear() {
		silent = true;
		super.clear();
		silent = false;
	}
	
	@SuppressWarnings("unchecked")
	protected E get(int index) {
		return (E)elements[index];
	}
	
	private class AutoRemovingIterator implements Iterator<E> {
		
		private int index = 0;
		
		@Override
		public boolean hasNext() {
			return index < tailIndex;
		}
		
		@Override
		public E next() {
			if(index >= tailIndex) throw new NoSuchElementException();
			return get(index++);
		}
		
	}
	
}
