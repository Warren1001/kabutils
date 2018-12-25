package com.kabryxis.kabutils.spigot.listener;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;

public class CancelRegisteredListener extends RegisteredListener {
	
	public CancelRegisteredListener(EventPriority priority, Plugin plugin) {
		super(null, null, priority, plugin, true);
	}
	
	@Override
	public void callEvent(Event event) {
		((Cancellable)event).setCancelled(true);
	}
	
}
