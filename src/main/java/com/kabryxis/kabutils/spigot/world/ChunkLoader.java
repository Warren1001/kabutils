package com.kabryxis.kabutils.spigot.world;

import org.bukkit.Chunk;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ChunkLoader {
	
	private final Map<Object, Set<Chunk>> loadedChunks = new HashMap<>();
	
	public void keepInMemory(Object key, Chunk chunk) {
		loadedChunks.computeIfAbsent(key, o -> new HashSet<>()).add(chunk);
	}
	
	public boolean shouldStayLoaded(Chunk chunk) {
		return loadedChunks.values().stream().anyMatch(set -> set.contains(chunk));
	}
	
	public void releaseFromMemory(Object key) {
		loadedChunks.get(key).clear();
	}
	
}
