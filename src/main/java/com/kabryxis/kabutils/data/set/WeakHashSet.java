package com.kabryxis.kabutils.data.set;

import java.util.*;

public class WeakHashSet<E> extends AbstractSet<E> implements Set<E> {
	
	private static final Object PRESENT = new Object();
	
	private transient WeakHashMap<E, Object> map;
	
	public WeakHashSet() {
		map = new WeakHashMap<>();
	}
	
	public WeakHashSet(Collection<? extends E> c) {
		map = new WeakHashMap<>(Math.max((int) (c.size()/.75f) + 1, 16));
		addAll(c);
	}
	
	public WeakHashSet(int initialCapacity, float loadFactor) {
		map = new WeakHashMap<>(initialCapacity, loadFactor);
	}
	
	public WeakHashSet(int initialCapacity) {
		map = new WeakHashMap<>(initialCapacity);
	}
	
	@Override
	public Iterator<E> iterator() {
		return map.keySet().iterator();
	}
	
	@Override
	public int size() {
		return map.size();
	}
	
	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}
	
	@Override
	public boolean contains(Object o) {
		return map.containsKey(o);
	}
	
	@Override
	public boolean add(E e) {
		return map.put(e, PRESENT) == null;
	}
	
	@Override
	public boolean remove(Object o) {
		return map.remove(o) == PRESENT;
	}
	
	@Override
	public void clear() {
		map.clear();
	}
	
	@Override
	public Object clone() {
		throw new UnsupportedOperationException();
	}
	
	/*private void writeObject(java.io.ObjectOutputStream s)
			throws java.io.IOException {
		// Write out any hidden serialization magic
		s.defaultWriteObject();
		
		// Write out HashMap capacity and load factor
		s.writeInt(map.capacity());
		s.writeFloat(map.loadFactor());
		
		// Write out size
		s.writeInt(map.size());
		
		// Write out all elements in the proper order.
		for (E e : map.keySet())
			s.writeObject(e);
	}
	
	private void readObject(java.io.ObjectInputStream s)
			throws java.io.IOException, ClassNotFoundException {
		// Read in any hidden serialization magic
		s.defaultReadObject();
		
		// Read capacity and verify non-negative.
		int capacity = s.readInt();
		if (capacity < 0) {
			throw new InvalidObjectException("Illegal capacity: " +
					capacity);
		}
		
		// Read load factor and verify positive and non NaN.
		float loadFactor = s.readFloat();
		if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
			throw new InvalidObjectException("Illegal load factor: " +
					loadFactor);
		}
		
		// Read size and verify non-negative.
		int size = s.readInt();
		if (size < 0) {
			throw new InvalidObjectException("Illegal size: " +
					size);
		}
		// Set the capacity according to the size and load factor ensuring that
		// the HashMap is at least 25% full but clamping to maximum capacity.
		capacity = (int) Math.min(size * Math.min(1 / loadFactor, 4.0f),
				HashMap.MAXIMUM_CAPACITY);
		
		// Constructing the backing map will lazily create an array when the first element is
		// added, so check it before construction. Call HashMap.tableSizeFor to compute the
		// actual allocation size. Check Map.Entry[].class since it's the nearest public type to
		// what is actually created.
		
		SharedSecrets.getJavaOISAccess()
				.checkArray(s, Map.Entry[].class, HashMap.tableSizeFor(capacity));
		
		// Create backing HashMap
		map = (((HashSet<?>)this) instanceof LinkedHashSet ?
				new LinkedHashMap<E,Object>(capacity, loadFactor) :
				new HashMap<E,Object>(capacity, loadFactor));
		
		// Read in all elements in the proper order.
		for (int i=0; i<size; i++) {
			@SuppressWarnings("unchecked")
			E e = (E) s.readObject();
			map.put(e, PRESENT);
		}
	}*/
	
}