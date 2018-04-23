package com.kabryxis.kabutils.spigot.world;

import org.bukkit.Chunk;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ChunkLoader { // TODO make interface that has an instance, rather than static accessor
	
	private static final Map<Object, Set<Chunk>> loadedChunks = new HashMap<>();
	
	public static void keepInMemory(Object key, Chunk chunk) {
		loadedChunks.computeIfAbsent(key, o -> new HashSet<>()).add(chunk);
	}
	
	public static boolean shouldStayLoaded(Chunk chunk) {
		return loadedChunks.values().stream().anyMatch(set -> set.contains(chunk));
	}
	
	public static void releaseFromMemory(Object key) {
		loadedChunks.get(key).clear();
	}
	
}
