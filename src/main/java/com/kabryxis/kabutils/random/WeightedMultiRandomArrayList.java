package com.kabryxis.kabutils.random;

import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class WeightedMultiRandomArrayList<K, V extends Weighted> extends MultiRandomArrayList<K, V> {
	
	public WeightedMultiRandomArrayList(Supplier<Map<K, List<V>>> supplier, Function<? super V, ? extends K> identifier, int noRepeat) {
		super(supplier, identifier, noRepeat);
	}
	
	public WeightedMultiRandomArrayList(Supplier<Map<K, List<V>>> supplier, Function<? super V, ? extends K> identifier) {
		super(supplier, identifier);
	}
	
	@Override
	public V random(K[] keys) {
		Validate.notNull(keys, "keys cannot be null");
		Validate.isTrue(keys.length != 0, "keys cannot be empty");
		V value;
		if(keys.length == 1) {
			K key = keys[0];
			List<V> values = map.get(key);
			if(values == null) throw new IllegalStateException("No values for the key " + key.toString() + ".");
			if(values.isEmpty()) return tryUsed(keys);
			//Collections.shuffle(values);
			int index = values.size() == 1 ? 0 : getWeightedIndex(values);
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
			//Collections.shuffle(combined);
			value = combined.get(combined.size() == 1 ? 0 : getWeightedIndex(combined));
			List<V> belonging = map.get(identifier.apply(value));
			belonging.remove(value);
			used.add(getListEntry(value, belonging));
		}
		if(used.size() == currNoRepeat + 1) used.remove(0).addBack();
		return value;
	}
	
	@Override
	public V random(List<K> keys) {
		Validate.notNull(keys, "keys cannot be null");
		Validate.isTrue(!keys.isEmpty(), "keys cannot be empty");
		V value;
		if(keys.size() == 1) {
			K key = keys.get(0);
			List<V> values = map.get(key);
			if(values == null) throw new IllegalStateException("No values for the key " + key.toString() + ".");
			if(values.isEmpty()) return tryUsed(keys);
			//Collections.shuffle(values);
			int index = values.size() == 1 ? 0 : getWeightedIndex(values);
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
			//Collections.shuffle(combined);
			value = combined.get(combined.size() == 1 ? 0 : getWeightedIndex(combined));
			List<V> belonging = map.get(identifier.apply(value));
			belonging.remove(value);
			used.add(getListEntry(value, belonging));
		}
		if(used.size() == currNoRepeat + 1) used.remove(0).addBack();
		return value;
	}
	
	protected int getWeightedIndex(List<V> values) {
		int totalWeight = 0;
		for(V value : values) {
			totalWeight += value.getWeight();
		}
		int chosenRarity = random.nextInt(totalWeight);
		int currentWeight = 0;
		for(int index = 0; index < values.size(); index++) {
			V value = values.get(index);
			currentWeight += value.getWeight();
			if(currentWeight >= chosenRarity) return index;
		}
		System.out.println("for some reason the weighted system failed");
		return random.nextInt(values.size());
	}

}
