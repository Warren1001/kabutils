package com.kabryxis.kabutils.spigot.inventory.itemstack;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Items {
	
	public static boolean exists(ItemStack item) {
		return item != null && item.getType() != Material.AIR;
	}
	
	public static boolean isType(ItemStack item, Material type) {
		return item != null && item.getType() == type;
	}
	
	public static ItemStack[] deepClone(ItemStack[] items) {
		ItemStack[] cloned = new ItemStack[items.length];
		for(int i = 0; i < items.length; i++) {
			ItemStack item = items[i];
			cloned[i] = item == null ? null : items[i].clone();
		}
		return cloned;
	}
	
}
