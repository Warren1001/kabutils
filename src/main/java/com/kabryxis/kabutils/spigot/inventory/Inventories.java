package com.kabryxis.kabutils.spigot.inventory;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Inventories {
	
	public static Inventory createInventory(String name, int rows, ItemStack[] contents) {
		Inventory inv = Bukkit.createInventory(null, rows * 9, name);
		inv.setContents(contents);
		return inv;
	}
	
}
