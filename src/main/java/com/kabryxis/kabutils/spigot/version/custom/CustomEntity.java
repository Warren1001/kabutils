package com.kabryxis.kabutils.spigot.version.custom;

import org.bukkit.entity.Entity;

public interface CustomEntity {
	
	void forceRemove();
	
	Entity getBukkitEntity();
	
}
