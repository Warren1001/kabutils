package com.kabryxis.kabutils.spigot.event;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public interface GlobalListener extends Listener {
	
	public void onEvent(Event event);
	
	public Plugin getPlugin();
	
}
