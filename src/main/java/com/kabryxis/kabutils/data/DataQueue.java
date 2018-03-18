package com.kabryxis.kabutils.data;

import java.util.ArrayList;

public class DataQueue<T> extends ArrayList<T> {

	private int currentIndex = -1;
	
	public int getCurrentIndex() {
		return currentIndex;
	}
	
	public void nextIndex() {
		currentIndex++;
	}
	
	public void resetIndex() {
		currentIndex = -1;
	}
	
	public T getCurrent() {
		return get(currentIndex);
	}
	
	public T getNext() {
		return get(currentIndex + 1);
	}
	
	public T getPrevious() {
		return get(currentIndex - 1);
	}
	
	public boolean hasNext() {
		return currentIndex < size() - 1;
	}
	
	public boolean hasPrevious() {
		return currentIndex > 0;
	}

}
