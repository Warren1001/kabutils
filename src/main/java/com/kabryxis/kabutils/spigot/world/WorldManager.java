package com.kabryxis.kabutils.spigot.world;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Chunk;
import org.bukkit.World;

import com.kabryxis.kabutils.spigot.version.wrapper.world.world.WrappedWorld;

public class WorldManager {
	
	private static final Map<World, WrappedWorld<?>> worlds = new ConcurrentHashMap<>();
	private static final Map<String, Set<Long>> memoryChunks = new ConcurrentHashMap<>();
	
	public static WrappedWorld<?> getWorld(World world) {
		return worlds.computeIfAbsent(world, s -> WrappedWorld.newInstance(world));
	}
	
	public static void keepChunkInMemory(String string, Chunk chunk) {
		keepChunkInMemory(string, getKey(chunk));
	}
	
	public static void keepChunkInMemory(String string, Long key) {
		Set<Long> chunks = memoryChunks.computeIfAbsent(string, s -> ConcurrentHashMap.newKeySet());
		if(!chunks.contains(key)) chunks.add(key);
	}
	
	public static void removeChunkFromMemory(String string, Chunk chunk) {
		removeChunkFromMemory(string, getKey(chunk));
	}
	
	public static void removeChunkFromMemory(String string, Long key) {
		Set<Long> chunks = memoryChunks.get(string);
		if(chunks != null) chunks.remove(key);
	}
	
	public static void removeChunksFromMemory(String string) {
		Set<Long> chunks = memoryChunks.get(string);
		if(chunks != null) chunks.clear();
	}
	
	public static boolean shouldChunkStayLoaded(Chunk chunk) {
		return shouldChunkStayLoaded(getKey(chunk));
	}
	
	public static boolean shouldChunkStayLoaded(Long key) {
		for(Set<Long> chunks : memoryChunks.values()) {
			if(chunks.stream().anyMatch(key::equals)) return true;
		}
		return false;
	}
	
	public static Long getKey(Chunk chunk) {
		return getWorld(chunk.getWorld()).toLong(chunk.getX(), chunk.getZ());
	}
	
}
