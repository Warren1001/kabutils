package com.kabryxis.kabutils.spigot.listener;

import com.kabryxis.kabutils.NoArgPredicate;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.Plugin;

public class PredicateCancelRegisteredListener extends CancelRegisteredListener {
	
	protected final NoArgPredicate predicate;
	
	public PredicateCancelRegisteredListener(EventPriority priority, Plugin plugin, NoArgPredicate predicate) {
		super(priority, plugin);
		this.predicate = predicate;
	}
	
	@Override
	public void callEvent(Event event) {
		if(predicate.test()) super.callEvent(event);
	}
	
}
