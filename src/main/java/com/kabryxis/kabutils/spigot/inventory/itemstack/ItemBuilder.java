package com.kabryxis.kabutils.spigot.inventory.itemstack;

import com.kabryxis.kabutils.data.Arrays;
import com.kabryxis.kabutils.data.Lists;
import com.kabryxis.kabutils.data.Maps;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemBuilder implements Cloneable {
	
	private static long UID_COUNTER = 1L;
	
	/**
	 * This can only be used in #new(ItemBuilder) method or #reset(ItemBuilder) on a different ItemBuilder object.
	 * Using any standard methods will have no effect.
	 * Using #clone or #build will throw UnsupportedOperationException.
	 */
	public static final ItemBuilder EMPTY = new ItemBuilder((ItemBuilder)null) {
		
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
		public ItemBuilder lore(Iterable<String> lines) {
			return this;
		}
		
		@Override
		public ItemBuilder lore(boolean append, String... lines) {
			return this;
		}
		
		@Override
		public ItemBuilder lore(boolean append, Iterable<String> line) {
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
		public ItemBuilder clone() {
			throw new UnsupportedOperationException("cannot clone empty ItemBuilder");
		}
		
		@Override
		public ItemStack build() {
			throw new UnsupportedOperationException("cannot generate ItemStack from empty ItemBuilder");
		}
		
		@Override
		public ItemStack build(ItemStack base) {
			throw new UnsupportedOperationException("cannot generate ItemStack from empty ItemBuilder");
		}
		
		@Override
		public ConfigSection serialize() {
			return null;
		}
		
		@Override
		public boolean isOf(ItemStack itemStack, ItemCompareFlag... flags) {
			return false;
		}
		
		@Override
		public boolean isOf(ItemStack itemStack, Collection<ItemCompareFlag> flags) {
			return false;
		}
		
		@Override
		public boolean isOf(ItemStack itemStack, ItemCompareFlag compareFlag) {
			return false;
		}
		
	};
	public static final ItemBuilder DEFAULT = new ItemBuilder(EMPTY).amount(1).prefix("").name("");
	
	public ItemBuilder(ItemBuilder base) {
		if(base != null) reset(base);
	}
	
	public ItemBuilder() {
		this(DEFAULT);
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
		this(section.getEnum("type", Material.class), section.getInt("amount", 1));
		int data = section.getInt("data", -1);
		if(data != -1) data(data);
		String prefix = section.get("prefix");
		if(prefix != null) prefix(prefix);
		String name = section.get("name");
		if(name != null) name(name);
		List<String> lore = section.getList("lore", String.class);
		if(lore != null) lore(true, lore);
		ConfigSection enchantsSection = section.get("enchants");
		if(enchantsSection != null) enchantsSection.forEach((enchantName, o) -> enchant(Enchantment.getByName(enchantName.toUpperCase()), (Integer)o));
		List<ItemFlag> flags = section.getList("flags", o -> ItemFlag.valueOf(o.toString().toUpperCase().replace(' ', '_')));
		if(flags != null) flags.forEach(this::flag);
		ConfigSection customSection = section.get("custom");
		if(customSection != null) customSection.forEach(this::custom);
		String uid = section.get("uid");
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
	
	private byte data = -1;
	
	public ItemBuilder data(byte data) {
		this.data = data;
		return this;
	}
	
	public ItemBuilder data(int data) {
		return data((byte)data);
	}
	
	private String prefix;
	
	public ItemBuilder prefix(String prefix) {
		this.prefix = prefix;
		return this;
	}
	
	private String name;
	
	public ItemBuilder name(String name) {
		this.name = name;
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
		Arrays.forEach(lines, lore::add);
		return this;
	}
	
	public ItemBuilder lore(Iterable<String> lines) {
		return lore(false, lines);
	}
	
	public ItemBuilder lore(boolean append, Iterable<String> lines) {
		if(lore == null) lore = new ArrayList<>();
		else if(!append) lore.clear();
		lines.forEach(lore::add);
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
		ItemStack item = new ItemStack(type, amount, (byte)Math.max(data, 0));
		if(name != null || lore != null || enchants != null || flags != null) {
			ItemMeta meta = item.getItemMeta();
			if(!prefix.isEmpty() || !name.isEmpty()) meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', prefix + name));
			if(lore != null) meta.setLore(lore.stream().map(s -> ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList()));
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
	
	public ItemStack build(ItemStack base) {
		Validate.notNull(base, "Cannot build onto a null ItemStack");
		if(type != null) {
			// TODO
		}
		if(amount != 0) base.setAmount(amount);
		if(data != -1) {
			// TODO
		}
		if(name != null || lore != null || enchants != null || flags != null) {
			ItemMeta meta = base.getItemMeta();
			if(!prefix.isEmpty() || !name.isEmpty()) meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', prefix + name));
			if(lore != null) {
				if(meta.hasLore()) {
					List<String> lore = meta.getLore();
					this.lore.forEach(s -> lore.add(ChatColor.translateAlternateColorCodes('&', s)));
					meta.setLore(lore);
				}
				else meta.setLore(lore.stream().map(s -> ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList()));
			}
			if(enchants != null) enchants.forEach((key, value) -> meta.addEnchant(key, value, true));
			if(flags != null) flags.forEach(meta::addItemFlags);
			base.setItemMeta(meta);
		}
		if(custom != null || uidKey != null) {
			WrappedItemStack wrappedItemStack = WrappedItemStack.newInstance(base);
			base = wrappedItemStack.getBukkitItemStack();
			WrappedNBTTagCompound tag = wrappedItemStack.getTag(true);
			if(custom != null) custom.forEach(tag::set);
			if(uidKey != null) tag.set(uidKey, UID_COUNTER++);
		}
		return base;
	}
	
	@Override
	public ItemBuilder clone() {
		ItemBuilder newBuilder;
		try {
			newBuilder = (ItemBuilder)super.clone();
		} catch(CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
		if(lore != null) newBuilder.lore = new ArrayList<>(lore);
		if(enchants != null) newBuilder.enchants = new HashMap<>(enchants);
		if(flags != null) newBuilder.flags = new HashSet<>(flags);
		if(custom != null) newBuilder.custom = new HashMap<>(custom);
		return newBuilder;
	}
	
	public boolean isOf(ItemStack itemStack, ItemCompareFlag... flags) {
		return Stream.of(flags).allMatch(flag -> isOf(itemStack, flag));
	}
	
	public boolean isOf(ItemStack itemStack, Collection<ItemCompareFlag> flags) {
		return flags.stream().allMatch(flag -> isOf(itemStack, flag));
	}
	
	public boolean isOf(ItemStack itemStack, ItemCompareFlag compareFlag) {
		if(itemStack == null) return false;
		switch(Validate.notNull(compareFlag, "compareFlag cannot be null")) {
			case TYPE:
				return type == null || type == itemStack.getType();
			case DATA:
				return data == itemStack.getData().getData();
			case NAME:
				String fullName = ChatColor.translateAlternateColorCodes('&', (prefix != null ? prefix : "") + (name != null ? name : ""));
				return fullName.isEmpty() ? !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasDisplayName() || itemStack.getItemMeta().getDisplayName().equals(fullName)
						: itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName() && itemStack.getItemMeta().getDisplayName().equals(fullName);
			case AMOUNT:
				return amount == itemStack.getAmount();
			case AMOUNT_MIN:
				return itemStack.getAmount() >= amount;
			default:
				throw new RuntimeException(String.format("ItemCompareFlag.%s has not been implemented in %s#isOf(ItemStack, ItemCompareFlag)", compareFlag.name(), getClass().getName()));
		}
	}
	
	public ConfigSection serialize() {
		ConfigSection section = new ConfigSection();
		if(type != null) section.put("type", type);
		if(amount != 1) section.put("amount", amount);
		if(data != 0) section.put("data", data);
		if(prefix != null && !prefix.isEmpty()) section.put("prefix", prefix);
		if(name != null && !name.isEmpty()) section.put("name", name);
		if(lore != null && !lore.isEmpty()) section.put("lore", lore);
		if(enchants != null && !enchants.isEmpty()) section.put("enchants", new ConfigSection(Maps.convert(enchants, e -> e.getName().toLowerCase(), i -> i)));
		if(flags != null && !flags.isEmpty()) section.put("flags", Lists.convert(new ArrayList<>(flags), o -> ((ItemFlag)o).name()));
		if(custom != null && !custom.isEmpty()) section.put("custom", new ConfigSection(custom));
		if(uidKey != null && !uidKey.isEmpty()) section.put("uid", uidKey);
		return section;
	}
	
	public enum ItemCompareFlag {
		
		TYPE, DATA, AMOUNT, AMOUNT_MIN, NAME
		
	}
	
}
