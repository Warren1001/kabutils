package com.kabryxis.kabutils.spigot.version.custom.player;

import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public interface ItemStackForwarder {
	
	void forward(Collection<ItemStack> items);
	
}
