package com.kabryxis.kabutils.spigot.entity;

import com.kabryxis.kabutils.cache.Cache;
import com.kabryxis.kabutils.spigot.concurrent.DelayedAction;
import org.bukkit.entity.Entity;

public class DelayedEntityRemover implements DelayedAction {
	
	private Entity entity;
	private long removeTime;
	
	public void reuse(Entity entity, long removeTime) {
		this.entity = entity;
		this.removeTime = removeTime;
	}
	
	@Override
	public boolean test() {
		if(System.currentTimeMillis() >= removeTime) {
			cache();
			return true;
		}
		return false;
	}
	
	@Override
	public void cache() {
		entity.remove();
		entity = null;
		removeTime = 0L;
		Cache.cache(this);
	}
	
}
