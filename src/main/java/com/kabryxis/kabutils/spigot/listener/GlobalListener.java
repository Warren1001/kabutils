package com.kabryxis.kabutils.spigot.listener;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;

public interface GlobalListener extends Listener {
	
	void onEvent(Event event);
	
}
