package com.kabryxis.kabutils.spigot.inventory.itemstack;

import com.kabryxis.kabutils.data.file.yaml.ConfigSection;
import com.kabryxis.kabutils.spigot.version.wrapper.item.itemstack.WrappedItemStack;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.WrappedNBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ItemBuilder implements Cloneable {
	
	public static final ItemBuilder DEFAULT = new ItemBuilder();
	
	public static ItemBuilder newItemBuilder() {
		return DEFAULT.clone();
	}
	
	public static ItemBuilder newItemBuilder(Material type) {
		return newItemBuilder().type(type);
	}
	
	public static ItemBuilder newItemBuilder(Material type, int amount) {
		return newItemBuilder(type).amount(amount);
	}
	
	public static ItemBuilder newItemBuilder(ConfigSection section) {
		ItemBuilder builder = newItemBuilder(Material.getMaterial(section.get("type", String.class).toUpperCase()));
		builder.amount(section.get("amount", Integer.class, 1));
		String name = section.get("name", String.class);
		if(name != null) builder.name(ChatColor.translateAlternateColorCodes('&', name));
		List<String> lore = section.getList("lore", String.class);
		if(lore != null) builder.lore(lore, true);
		ConfigSection enchantsSection = section.get("enchants", ConfigSection.class);
		if(enchantsSection != null) enchantsSection.getValues(false).forEach((enchantName, o) -> builder.enchant(Enchantment.getByName(enchantName.toUpperCase()), (Integer)o));
		ConfigSection customSection = section.get("custom", ConfigSection.class);
		if(customSection != null) customSection.getValues(false).forEach(builder::custom);
		return builder;
	}
	
	private ItemBuilder() {}
	
	private Material type;
	
	public ItemBuilder type(Material type) {
		this.type = type;
		return this;
	}
	
	public Material type() {
		return type;
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
	
	private String prefix = "";
	
	public ItemBuilder prefix(String prefix) {
		this.prefix = ChatColor.translateAlternateColorCodes('&', prefix);
		return this;
	}
	
	private String name = "";
	
	public ItemBuilder name(String name) {
		this.name = ChatColor.translateAlternateColorCodes('&', name);
		return this;
	}
	
	public String name() {
		return name;
	}
	
	private List<String> lore;
	
	public ItemBuilder lore(String... lines) {
		lore = new ArrayList<>();
		for(String line : lines) {
			lore.add(ChatColor.translateAlternateColorCodes('&', line));
		}
		return this;
	}
	
	public ItemBuilder lore(List<String> lines) {
		return lore(lines, false);
	}
	
	public ItemBuilder lore(List<String> lines, boolean append) {
		if(lore == null) lore = new ArrayList<>(lines.size());
		else if(!append) lore.clear();
		lines.forEach(line -> lore.add(ChatColor.translateAlternateColorCodes('&', line)));
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
	
	private Map<String, Object> custom;
	
	public ItemBuilder custom(String key, Object obj) {
		if(custom == null) custom = new HashMap<>();
		custom.put(key, obj);
		return this;
	}
	
	public ItemBuilder reset() {
		type = null;
		amount = 1;
		data = 0;
		prefix = "";
		name = "";
		lore = null;
		enchants = null;
		flags = null;
		custom = null;
		return this;
	}
	
	public ItemStack build() {
		ItemStack item = new ItemStack(type, amount, data);
		if(name != null || lore != null || enchants != null || flags != null) {
			ItemMeta meta = item.getItemMeta();
			if(!prefix.isEmpty() || !name.isEmpty()) meta.setDisplayName(prefix + name);
			if(lore != null) meta.setLore(lore);
			if(enchants != null) enchants.forEach((key, value) -> meta.addEnchant(key, value, true));
			if(flags != null) flags.forEach(meta::addItemFlags);
			item.setItemMeta(meta);
		}
		if(custom != null) {
			WrappedItemStack wrappedItemStack = WrappedItemStack.newInstance(item);
			item = wrappedItemStack.getBukkitItemStack();
			WrappedNBTTagCompound tag = wrappedItemStack.getTag(true);
			custom.forEach(tag::set);
		}
		return item;
	}
	
	@Override
	public ItemBuilder clone() {
		ItemBuilder newBuilder;
		try {
			newBuilder = (ItemBuilder)super.clone();
		} catch(CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
		newBuilder.type = type;
		newBuilder.amount = amount;
		newBuilder.data = data;
		newBuilder.prefix = prefix;
		newBuilder.name = name;
		if(lore != null) newBuilder.lore = new ArrayList<>(lore);
		if(enchants != null) newBuilder.enchants = new HashMap<>(enchants);
		if(flags != null) newBuilder.flags = new HashSet<>(flags);
		return newBuilder;
	}
	
	public boolean isOf(ItemStack itemStack, ItemCompareFlag... flags) {
		for(ItemCompareFlag compareFlag : flags) {
			switch(compareFlag) {
				case TYPE:
					if(type != itemStack.getType()) return false;
					break;
				case DATA:
					if(data != itemStack.getData().getData()) return false;
					break;
				case NAME:
					if(!itemStack.hasItemMeta() || !itemStack.getItemMeta().hasDisplayName() ||
							!itemStack.getItemMeta().getDisplayName().equals(prefix + name)) return false;
					break;
				case COLORLESS_NAME:
					if(!itemStack.hasItemMeta() || !itemStack.getItemMeta().hasDisplayName() ||
							!ChatColor.stripColor(itemStack.getItemMeta().getDisplayName()).equals(ChatColor.stripColor(prefix + name))) return false;
					break;
				case AMOUNT:
					if(amount != itemStack.getAmount()) return false;
					break;
				case AMOUNT_MIN:
					if(itemStack.getAmount() < amount) return false;
					break;
			}
		}
		return true;
	}
	
	public enum ItemCompareFlag {
		
		TYPE, DATA, AMOUNT, AMOUNT_MIN, NAME, COLORLESS_NAME
		
	}
	
}
