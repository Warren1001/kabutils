package com.kabryxis.kabutils.spigot.inventory.itemstack;

import com.kabryxis.kabutils.data.file.yaml.ConfigSection;
import com.kabryxis.kabutils.spigot.version.wrapper.item.itemstack.WrappedItemStack;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.WrappedNBTTagCompound;
import org.apache.commons.lang3.Validate;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ItemBuilder implements Cloneable {
	
	private static long UID_COUNTER = 1L;
	
	public static final ItemBuilder DEFAULT = new ItemBuilder(true).amount(1).prefix("").name("");
	/**
	 * This can only be used in ItemBuilder#reset(ItemBuilder) method or ItemStack#clone. Using any standard methods will have no effect.
	 */
	public static final ItemBuilder EMPTY = new ItemBuilder(true) {
		
		@Override
		public ItemBuilder type(Material type) {
			return this;
		}
		
		@Override
		public ItemBuilder amount(int amount) {
			return this;
		}
		
		@Override
		public ItemBuilder custom(String key, Object obj) {
			return this;
		}
		
		@Override
		public ItemBuilder data(byte data) {
			return this;
		}
		
		@Override
		public ItemBuilder data(int data) {
			return this;
		}
		
		@Override
		public ItemBuilder enchant(Enchantment enchant, int i) {
			return this;
		}
		
		@Override
		public ItemBuilder flag(ItemFlag flag) {
			return this;
		}
		
		@Override
		public ItemBuilder lore(String... lines) {
			return this;
		}
		
		@Override
		public ItemBuilder lore(List<String> lines) {
			return this;
		}
		
		@Override
		public ItemBuilder lore(boolean append, String... lines) {
			return this;
		}
		
		@Override
		public ItemBuilder lore(List<String> lines, boolean append) {
			return this;
		}
		
		@Override
		public ItemBuilder name(String name) {
			return this;
		}
		
		@Override
		public ItemBuilder prefix(String prefix) {
			return this;
		}
		
		@Override
		public ItemBuilder reset() {
			return this;
		}
		
		@Override
		public ItemBuilder reset(ItemBuilder builder) {
			return this;
		}
		
		@Override
		public ItemBuilder uid(String key) {
			return this;
		}
		
		@Override
		public ItemStack build() {
			throw new UnsupportedOperationException("Cannot generate ItemStack from designated empty ItemBuilder");
		}
		
	};
	
	public ItemBuilder(boolean empty) {
		if(!empty) reset();
	}
	
	public ItemBuilder() {
		this(false);
	}
	
	public ItemBuilder(Material type) {
		this();
		type(type);
	}
	
	public ItemBuilder(Material type, int amount) {
		this(type);
		amount(amount);
	}
	
	public ItemBuilder(ConfigSection section) {
		this(section.get("type", Material.class), section.get("amount", Integer.class, 1));
		String name = section.get("name", String.class);
		if(name != null) name(name);
		List<String> lore = section.getList("lore", String.class);
		if(lore != null) lore(lore, true);
		ConfigSection enchantsSection = section.get("enchants", ConfigSection.class);
		if(enchantsSection != null) enchantsSection.getValues(false).forEach((enchantName, o) -> enchant(Enchantment.getByName(enchantName.toUpperCase()), (Integer)o));
		ConfigSection customSection = section.get("custom", ConfigSection.class);
		if(customSection != null) customSection.getValues(false).forEach(this::custom);
		String uid = section.get("uuid", String.class);
		if(uid != null) uid(uid);
	}
	
	private Material type;
	
	public ItemBuilder type(Material type) {
		this.type = type;
		return this;
	}
	
	public Material type() {
		return type;
	}
	
	private int amount;
	
	public ItemBuilder amount(int amount) {
		this.amount = amount;
		return this;
	}
	
	private byte data;
	
	public ItemBuilder data(byte data) {
		this.data = data;
		return this;
	}
	
	public ItemBuilder data(int data) {
		return data((byte)data);
	}
	
	private String prefix;
	
	public ItemBuilder prefix(String prefix) {
		this.prefix = ChatColor.translateAlternateColorCodes('&', prefix);
		return this;
	}
	
	private String name;
	
	public ItemBuilder name(String name) {
		this.name = ChatColor.translateAlternateColorCodes('&', name);
		return this;
	}
	
	public String name() {
		return name;
	}
	
	private List<String> lore;
	
	public ItemBuilder lore(String... lines) {
		return lore(false, lines);
	}
	
	public ItemBuilder lore(boolean append, String... lines) {
		if(lore == null) lore = new ArrayList<>(lines.length);
		else if(!append) lore.clear();
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
	
	public ItemBuilder clearLore() {
		lore = null;
		return this;
	}
	
	private Map<Enchantment, Integer> enchants;
	
	public ItemBuilder enchant(Enchantment enchant, int i) {
		if(enchants == null) enchants = new HashMap<>();
		enchants.put(enchant, i);
		return this;
	}
	
	public ItemBuilder clearEnchants() {
		enchants = null;
		return this;
	}
	
	private Set<ItemFlag> flags;
	
	public ItemBuilder flag(ItemFlag flag) {
		if(flags == null) flags = new HashSet<>();
		flags.add(flag);
		return this;
	}
	
	public ItemBuilder clearFlags() {
		flags = null;
		return this;
	}
	
	private Map<String, Object> custom;
	
	public ItemBuilder custom(String key, Object obj) {
		if(custom == null) custom = new HashMap<>();
		custom.put(key, obj);
		return this;
	}
	
	public ItemBuilder clearCustoms() {
		custom = null;
		return this;
	}
	
	private String uidKey;
	
	/**
	 * Important note, this is not designed for multiple server-sessions. Items using UIDs should be considered temporary to a game instance.
	 * Trying to use it otherwise will essentially break the design, causing item UIDs to no longer be unique.
	 */
	public ItemBuilder uid(String key) {
		this.uidKey = key;
		return this;
	}
	
	public ItemBuilder reset() {
		return reset(ItemBuilder.DEFAULT);
	}
	
	public ItemBuilder reset(ItemBuilder builder) {
		this.type = builder.type;
		this.amount = builder.amount;
		this.data = builder.data;
		this.prefix = builder.prefix;
		this.name = builder.name;
		if(builder.lore != null) this.lore = new ArrayList<>(builder.lore);
		if(builder.enchants != null) this.enchants = new HashMap<>(builder.enchants);
		if(builder.flags != null) this.flags = new HashSet<>(builder.flags);
		if(builder.custom != null) this.custom = new HashMap<>(builder.custom);
		this.uidKey = builder.uidKey;
		return this;
	}
	
	public ItemStack build() {
		Validate.notNull(type, "Cannot build an ItemStack without a Material type");
		ItemStack item = new ItemStack(type, amount, data);
		if(name != null || lore != null || enchants != null || flags != null) {
			ItemMeta meta = item.getItemMeta();
			if(!prefix.isEmpty() || !name.isEmpty()) meta.setDisplayName(prefix + name);
			if(lore != null) meta.setLore(lore);
			if(enchants != null) enchants.forEach((key, value) -> meta.addEnchant(key, value, true));
			if(flags != null) flags.forEach(meta::addItemFlags);
			item.setItemMeta(meta);
		}
		if(custom != null || uidKey != null) {
			WrappedItemStack wrappedItemStack = WrappedItemStack.newInstance(item);
			item = wrappedItemStack.getBukkitItemStack();
			WrappedNBTTagCompound tag = wrappedItemStack.getTag(true);
			if(custom != null) custom.forEach(tag::set);
			if(uidKey != null) tag.set(uidKey, UID_COUNTER++);
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
		if(custom != null) newBuilder.custom = new HashMap<>(custom);
		newBuilder.uidKey = uidKey;
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
