package com.kabryxis.kabutils.spigot.inventory.itemstack;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Items {
	
	public static boolean exists(ItemStack item) {
		return item != null && item.getType() != Material.AIR;
	}
	
	public static boolean isType(ItemStack item, Material type) {
		return exists(item) && item.getType() == type;
	}
	
}
