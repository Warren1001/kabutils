package com.kabryxis.kabutils.random;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.commons.lang3.Validate;

import com.kabryxis.kabutils.data.Pair;

public class MultiRandomArrayList<K, V> {
	
	private final Random random;
	private final List<Pair<V, List<V>>> used;
	private final Map<K, List<V>> map;
	private final Function<? super V, ? extends K> identifier;
	
	private int size = 0;
	private int noRepeat;
	private int currNoRepeat;
	
	public MultiRandomArrayList(Supplier<Map<K, List<V>>> supplier, Function<? super V, ? extends K> identifier, int noRepeat) {
		this.random = new Random();
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
		boolean returnVal = list != null && !list.isEmpty() ? list.remove(value) : false;
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
	
	private void update() {
		if(currNoRepeat == size - 1) return;
		currNoRepeat = size <= noRepeat ? size - 1 : noRepeat;
		if(currNoRepeat < 1) {
			if(!used.isEmpty()) {
				for(Pair<V, List<V>> pair : used) {
					pair.getValue().add(pair.getKey());
				}
				used.clear();
			}
		}
		else {
			int over = used.size() - currNoRepeat + 1;
			if(over > 0) {
				for(; over > 0; over--) {
					Pair<V, List<V>> pair = used.remove(0);
					pair.getValue().add(pair.getKey());
				}
			}
		}
	}
	
	public V random(K[] keys) {
		V value = null;
		if(keys.length == 1) {
			K key = keys[0];
			List<V> values = map.get(key);
			if(values == null) throw new IllegalStateException("No values for the key " + key.toString() + ".");
			if(values.isEmpty()) return tryUsed(keys);
			int index = values.size() == 1 ? 0 : random.nextInt(values.size());
			value = currNoRepeat == 0 ? values.get(index) : values.remove(index);
			if(currNoRepeat != 0) used.add(new Pair<>(value, values));
		}
		else {
			List<V> combined = new ArrayList<>();
			for(K key : keys) {
				List<V> values = map.get(key);
				if(values != null && !values.isEmpty()) combined.addAll(values);
			}
			if(combined.isEmpty()) return tryUsed(keys);
			value = combined.remove(combined.size() == 1 ? 0 : random.nextInt(combined.size()));
			List<V> belonging = map.get(identifier.apply(value));
			belonging.remove(value);
			used.add(new Pair<>(value, belonging));
		}
		if(used.size() == currNoRepeat + 1) {
			Pair<V, List<V>> pair = used.remove(0);
			pair.getValue().add(pair.getKey());
		}
		return value;
	}
	
	public V random(List<K> keys) {
		Validate.notNull(keys, "keys cannot be null");
		Validate.isTrue(!keys.isEmpty(), "keys cannot be empty");
		V value = null;
		if(keys.size() == 1) {
			K key = keys.get(0);
			List<V> values = map.get(key);
			if(values == null) throw new IllegalStateException("No values for the key " + key.toString() + ".");
			if(values.isEmpty()) return tryUsed(keys);
			int index = values.size() == 1 ? 0 : random.nextInt(values.size());
			value = currNoRepeat == 0 ? values.get(index) : values.remove(index);
			if(currNoRepeat != 0) used.add(new Pair<>(value, values));
		}
		else {
			List<V> combined = new ArrayList<>();
			for(K key : keys) {
				List<V> values = map.get(key);
				if(values != null && !values.isEmpty()) combined.addAll(values);
			}
			if(combined.isEmpty()) return tryUsed(keys);
			value = combined.remove(combined.size() == 1 ? 0 : random.nextInt(combined.size()));
			List<V> belonging = map.get(identifier.apply(value));
			belonging.remove(value);
			used.add(new Pair<>(value, belonging));
		}
		if(used.size() == currNoRepeat + 1) {
			Pair<V, List<V>> pair = used.remove(0);
			pair.getValue().add(pair.getKey());
		}
		return value;
	}
	
	protected V tryUsed(K[] keys) {
		Pair<V, List<V>> use = null;
		boolean found = false;
		for(Iterator<Pair<V, List<V>>> iterator = used.iterator(); iterator.hasNext();) {
			use = iterator.next();
			K id = identifier.apply(use.getKey());
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
		return use.getKey();
	}
	
	protected V tryUsed(Iterable<K> keys) {
		Pair<V, List<V>> use = null;
		boolean found = false;
		for(Iterator<Pair<V, List<V>>> iterator = used.iterator(); iterator.hasNext();) {
			use = iterator.next();
			K id = identifier.apply(use.getKey());
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
		return use.getKey();
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
		used.forEach(p -> action.accept(p.getKey()));
	}
	
}
