package com.kabryxis.kabutils.spigot.listener;

import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.hanging.HangingEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.world.ChunkEvent;
import org.bukkit.event.world.WorldEvent;
import org.bukkit.plugin.EventExecutor;

public class WorldExecutor implements EventExecutor {
	
	private final World[] worlds;
	
	public WorldExecutor(World... worlds) {
		this.worlds = worlds;
	}
	
	public World[] getWorlds() {
		return worlds;
	}
	
	@Override
	public void execute(Listener listener, Event event) {
		if(listener instanceof GlobalListener) {
			if(!(event instanceof PlayerChangedWorldEvent) &&
					((event instanceof BlockEvent && !isWorldMonitored(((BlockEvent)event).getBlock().getWorld())) ||
					(event instanceof PlayerEvent && !isWorldMonitored(((PlayerEvent)event).getPlayer().getWorld())) ||
					(event instanceof EntityEvent && !isWorldMonitored(((EntityEvent)event).getEntity().getWorld())) ||
					(event instanceof ChunkEvent && !isWorldMonitored(((ChunkEvent)event).getChunk().getWorld())) ||
					(event instanceof WorldEvent && !isWorldMonitored(((WorldEvent)event).getWorld())) ||
					(event instanceof HangingEvent) && !isWorldMonitored(((HangingEvent)event).getEntity().getWorld()))) return;
			((GlobalListener)listener).onEvent(event);
		}
	}
	
	public boolean isWorldMonitored(World world) {
		for(World w : worlds) {
			if(w.getName().equals(world.getName())) return true;
		}
		return false;
	}
	
}
