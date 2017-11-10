package com.kabryxis.kabutils.spigot.inventory.itemstack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {
	
	public ItemBuilder() {}
	
	public ItemBuilder(Material type) {
		material(type);
	}
	
	public ItemBuilder(Material type, int amount) {
		this(type);
		amount(amount);
	}
	
	private Material type;
	
	public ItemBuilder material(Material type) {
		this.type = type;
		return this;
	}
	
	private int amount = 1;
	
	public ItemBuilder amount(int amount) {
		this.amount = amount;
		return this;
	}
	
	private byte data;
	
	public ItemBuilder data(byte data) {
		this.data = data;
		return this;
	}
	
	private String name;
	
	public ItemBuilder name(String name) {
		this.name = name;
		return this;
	}
	
	private Map<Enchantment, Integer> enchants;
	
	public ItemBuilder enchant(Enchantment enchant, int i) {
		if(enchants == null) enchants = new HashMap<>();
		enchants.put(enchant, i);
		return this;
	}
	
	private Set<ItemFlag> flags;
	
	public ItemBuilder flag(ItemFlag flag) {
		if(flags == null) flags = new HashSet<>();
		flags.add(flag);
		return this;
	}
	
	public ItemStack build() {
		ItemStack item = new ItemStack(type, amount, data);
		if(name != null || enchants != null || flags != null) {
			ItemMeta meta = item.getItemMeta();
			if(name != null) meta.setDisplayName(name);
			if(enchants != null) {
				enchants.entrySet().forEach(entry -> meta.addEnchant(entry.getKey(), entry.getValue().intValue(), true));
				enchants.clear();
			}
			if(flags != null) {
				meta.addItemFlags(flags.toArray(new ItemFlag[flags.size()]));
				flags.clear();
			}
			item.setItemMeta(meta);
		}
		return item;
	}
	
}
