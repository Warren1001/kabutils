package com.kabryxis.kabutils.spigot.version.custom.player;

import org.bukkit.inventory.ItemStack;

public interface ItemStackTracker {
	
	void track(ItemStack item);
	
	void untrack(ItemStack item);
	
	void untrackAll();
	
}
