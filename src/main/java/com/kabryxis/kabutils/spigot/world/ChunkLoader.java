package com.kabryxis.kabutils.spigot.world;

import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class ChunkLoader implements Listener {
	
	private final Map<Object, Set<Chunk>> loadedChunks = new HashMap<>();
	
	public ChunkLoader(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public void keepInMemory(Object key, Chunk chunk) {
		loadedChunks.computeIfAbsent(key, o -> new HashSet<>()).add(chunk);
	}
	
	public void keepInMemory(Object key, Collection<Chunk> chunks) {
		loadedChunks.computeIfAbsent(key, o -> new HashSet<>()).addAll(chunks);
	}
	
	public boolean shouldStayLoaded(Chunk chunk) {
		return loadedChunks.values().stream().anyMatch(set -> set.contains(chunk));
	}
	
	public void releaseFromMemory(Object key) {
		loadedChunks.remove(key);
	}
	
	@EventHandler
	public void onChunkUnload(ChunkUnloadEvent event) {
		if(shouldStayLoaded(event.getChunk())) event.setCancelled(true);
	}
	
}
