package com.kabryxis.kabutils.spigot.entity;

import com.kabryxis.kabutils.spigot.concurrent.DelayedAction;
import org.bukkit.entity.Entity;

public class DelayedEntityRemover implements DelayedAction {
	
	private final Entity entity;
	
	private long removeTime;
	
	public DelayedEntityRemover(Entity entity, long removeTime) {
		this.entity = entity;
		this.removeTime = removeTime;
	}
	
	@Override
	public boolean test() {
		if(System.currentTimeMillis() >= removeTime) {
			entity.remove();
			return true;
		}
		return false;
	}
	
}
