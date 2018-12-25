package com.kabryxis.kabutils.random;

import org.apache.commons.lang3.Validate;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class MultiRandomArrayList<K, V> {
	
	protected final Queue<ListEntry> cache = new ConcurrentLinkedQueue<>();
	protected final Random random = new Random();
	
	protected final List<ListEntry> used;
	protected final Map<K, List<V>> map;
	protected final Function<? super V, ? extends K> identifier;
	
	protected int size = 0;
	protected int noRepeat;
	protected int currNoRepeat;
	
	public MultiRandomArrayList(Supplier<Map<K, List<V>>> supplier, Function<? super V, ? extends K> identifier, int noRepeat) {
		this.used = new ArrayList<>(noRepeat);
		this.map = supplier.get();
		this.identifier = identifier;
		this.noRepeat = noRepeat;
		this.currNoRepeat = noRepeat;
	}
	
	public MultiRandomArrayList(Supplier<Map<K, List<V>>> supplier, Function<? super V, ? extends K> identifier) {
		this(supplier, identifier, 0);
	}
	
	public boolean addToList(K key, V value) {
		boolean returnVal = map.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
		if(returnVal) {
			size++;
			update();
		}
		return returnVal;
	}
	
	public boolean removeFromList(K key, V value) {
		List<V> list = map.get(key);
		boolean returnVal = (list != null && !list.isEmpty()) && list.remove(value);
		if(returnVal) {
			size--;
			update();
		}
		return returnVal;
	}
	
	public void setNoRepeat(int noRepeat) {
		this.noRepeat = noRepeat;
		update();
	}
	
	public int size() {
		return size;
	}
	
	protected void update() {
		if(currNoRepeat == size - 1) return;
		currNoRepeat = size <= noRepeat ? size - 1 : noRepeat;
		if(currNoRepeat < 1) {
			if(!used.isEmpty()) {
				used.forEach(ListEntry::addBack);
				used.clear();
			}
		}
		else {
			int over = used.size() - currNoRepeat + 1;
			if(over > 0) {
				for(; over > 0; over--) {
					used.remove(0).addBack();
				}
			}
		}
	}
	
	public V random(K[] keys) {
		Validate.notNull(keys, "keys cannot be null");
		Validate.isTrue(keys.length != 0, "keys cannot be empty");
		V value;
		if(keys.length == 1) {
			K key = keys[0];
			List<V> values = map.get(key);
			if(values == null) throw new IllegalStateException("No values for the key " + key.toString() + ".");
			if(values.isEmpty()) return tryUsed(keys);
			int index = values.size() == 1 ? 0 : random.nextInt(values.size());
			value = currNoRepeat == 0 ? values.get(index) : values.remove(index);
			if(currNoRepeat != 0) used.add(getListEntry(value, values));
		}
		else {
			List<V> combined = new ArrayList<>();
			for(K key : keys) {
				List<V> values = map.get(key);
				if(values != null && !values.isEmpty()) combined.addAll(values);
			}
			if(combined.isEmpty()) return tryUsed(keys);
			value = combined.get(combined.size() == 1 ? 0 : random.nextInt(combined.size()));
			List<V> belonging = map.get(identifier.apply(value));
			belonging.remove(value);
			used.add(getListEntry(value, belonging));
		}
		if(used.size() == currNoRepeat + 1) used.remove(0).addBack();
		return value;
	}
	
	public V random(List<K> keys) {
		Validate.notNull(keys, "keys cannot be null");
		Validate.isTrue(!keys.isEmpty(), "keys cannot be empty");
		V value;
		if(keys.size() == 1) {
			K key = keys.get(0);
			List<V> values = map.get(key);
			if(values == null) throw new IllegalStateException("No values for the key " + key.toString() + ".");
			if(values.isEmpty()) return tryUsed(keys);
			int index = values.size() == 1 ? 0 : random.nextInt(values.size());
			value = currNoRepeat == 0 ? values.get(index) : values.remove(index);
			if(currNoRepeat != 0) used.add(getListEntry(value, values));
		}
		else {
			List<V> combined = new ArrayList<>();
			for(K key : keys) {
				List<V> values = map.get(key);
				if(values != null && !values.isEmpty()) combined.addAll(values);
			}
			if(combined.isEmpty()) return tryUsed(keys);
			value = combined.get(combined.size() == 1 ? 0 : random.nextInt(combined.size()));
			List<V> belonging = map.get(identifier.apply(value));
			belonging.remove(value);
			used.add(getListEntry(value, belonging));
		}
		if(used.size() == currNoRepeat + 1) used.remove(0).addBack();
		return value;
	}
	
	protected V tryUsed(K[] keys) {
		ListEntry use = null;
		boolean found = false;
		for(Iterator<ListEntry> iterator = used.iterator(); iterator.hasNext();) {
			use = iterator.next();
			K id = identifier.apply(use.getObject());
			for(K key : keys) {
				if(key.equals(id)) {
					iterator.remove();
					found = true;
					break;
				}
			}
			if(found) break;
		}
		if(!found) throw new IllegalStateException("No values for the keys " + Arrays.toString(keys) + ".");
		used.add(use);
		return use.getObject();
	}
	
	protected V tryUsed(Iterable<K> keys) {
		ListEntry use = null;
		boolean found = false;
		for(Iterator<ListEntry> iterator = used.iterator(); iterator.hasNext();) {
			use = iterator.next();
			K id = identifier.apply(use.getObject());
			for(K key : keys) {
				if(key.equals(id)) {
					iterator.remove();
					found = true;
					break;
				}
			}
			if(found) break;
		}
		if(!found) throw new IllegalStateException("No values for the keys " + keys.toString() + ".");
		used.add(use);
		return use.getObject();
	}
	
	public List<V> random(K[][] keysArray) {
		List<V> list = new ArrayList<>(keysArray.length);
		for(K[] keys : keysArray) {
			list.add(random(keys));
		}
		return list;
	}
	
	public List<V> random(Collection<? extends K[]> collection) {
		List<V> list = new ArrayList<>(collection.size());
		for(K[] keys : collection) {
			list.add(random(keys));
		}
		return list;
	}
	
	public void forEachValue(Consumer<? super V> action) {
		map.values().forEach(list -> list.forEach(action));
		used.forEach(p -> action.accept(p.getObject()));
	}
	
	protected ListEntry getListEntry(V obj, List<V> belongingList) {
		ListEntry entry = cache.isEmpty() ? new ListEntry() : cache.poll();
		entry.reuse(obj, belongingList);
		return entry;
	}
	
	protected class ListEntry {
		
		private V obj;
		private List<V> belongingList;
		
		public void reuse(V obj, List<V> belongingList) {
			this.obj = obj;
			this.belongingList = belongingList;
		}
		
		public V getObject() {
			return obj;
		}
		
		public void addBack() {
			belongingList.add(obj);
			cache();
		}
		
		public void cache() {
			obj = null;
			belongingList = null;
			cache.add(this);
		}
		
	}
	
}
