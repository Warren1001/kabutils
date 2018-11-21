package com.kabryxis.kabutils.spigot.inventory.itemstack;

import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.WrappedNBTTagCompound;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.list.WrappedNBTTagList;
import org.apache.commons.lang3.Validate;
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
			if(item != null) cloned[i] = item.clone();
		}
		return cloned;
	}
	
	public static boolean isMirror(ItemStack item) {
		return item != null && item.getClass().getSimpleName().equals("CraftItemStack");
	}
	
	private static final ThreadLocal<WrappedNBTTagCompound> COMPOUND_TAG = ThreadLocal.withInitial(WrappedNBTTagCompound::newInstance);
	
	public static void setObject(ItemStack item, String key, Object data) {
		Validate.isTrue(exists(item), "item cannot be null or air");
		WrappedNBTTagCompound wrappedTag = COMPOUND_TAG.get().setHandle(item);
		wrappedTag.set(key, data);
		wrappedTag.clear();
	}
	
	public static Object getBaseRaw(ItemStack item, String key) {
		if(!exists(item) || !item.hasItemMeta()) return null;
		WrappedNBTTagCompound wrappedTag = COMPOUND_TAG.get().setHandle(item);
		Object obj = wrappedTag.getBaseRaw(key);
		wrappedTag.clear();
		return obj;
	}
	
	public static Object getCompoundRaw(ItemStack item, String key) {
		if(!exists(item) || !item.hasItemMeta()) return null;
		WrappedNBTTagCompound wrappedTag = COMPOUND_TAG.get().setHandle(item);
		Object obj = wrappedTag.getCompoundRaw(key);
		wrappedTag.clear();
		return obj;
	}
	
	public static WrappedNBTTagCompound getCompound(ItemStack item, String key, WrappedNBTTagCompound tag) {
		if(!exists(item) || !item.hasItemMeta()) return null;
		WrappedNBTTagCompound wrappedTag = COMPOUND_TAG.get().setHandle(item);
		wrappedTag.getCompound(key, tag);
		wrappedTag.clear();
		return tag;
	}
	
	public static WrappedNBTTagCompound getCompound(ItemStack item, String key) {
		if(!exists(item) || !item.hasItemMeta()) return null;
		WrappedNBTTagCompound wrappedTag = COMPOUND_TAG.get().setHandle(item);
		WrappedNBTTagCompound tag = wrappedTag.getCompound(key);
		wrappedTag.clear();
		return tag;
	}
	
	public static WrappedNBTTagList getList(ItemStack item, String key, int i, WrappedNBTTagList list) {
		if(!exists(item) || !item.hasItemMeta()) return null;
		WrappedNBTTagCompound wrappedTag = COMPOUND_TAG.get().setHandle(item);
		wrappedTag.getList(key, i, list);
		wrappedTag.clear();
		return list;
	}
	
	public static WrappedNBTTagList getList(ItemStack item, String key, int i) {
		if(!exists(item) || !item.hasItemMeta() || !item.getClass().getSimpleName().equals("CraftItemStack")) return null;
		WrappedNBTTagCompound wrappedTag = COMPOUND_TAG.get().setHandle(item);
		WrappedNBTTagList list = wrappedTag.getList(key, i);
		wrappedTag.clear();
		return list;
	}
	
	public static String getString(ItemStack item, String key) {
		if(!exists(item) || !item.hasItemMeta()) return null;
		WrappedNBTTagCompound wrappedTag = COMPOUND_TAG.get().setHandle(item);
		String string = wrappedTag.getString(key);
		wrappedTag.clear();
		return string;
	}
	
	public static int getInt(ItemStack item, String key) {
		if(!exists(item) || !item.hasItemMeta()) return 0;
		WrappedNBTTagCompound wrappedTag = COMPOUND_TAG.get().setHandle(item);
		int i = wrappedTag.getInt(key);
		wrappedTag.clear();
		return i;
	}
	
	public static double getDouble(ItemStack item, String key) {
		if(!exists(item) || !item.hasItemMeta()) return 0;
		WrappedNBTTagCompound wrappedTag = COMPOUND_TAG.get().setHandle(item);
		double d = wrappedTag.getDouble(key);
		wrappedTag.clear();
		return d;
	}
	
	public static float getFloat(ItemStack item, String key) {
		if(!exists(item) || !item.hasItemMeta()) return 0;
		WrappedNBTTagCompound wrappedTag = COMPOUND_TAG.get().setHandle(item);
		float f = wrappedTag.getFloat(key);
		wrappedTag.clear();
		return f;
	}
	
	public static boolean getBoolean(ItemStack item, String key) {
		if(!exists(item) || !item.hasItemMeta()) return false;
		WrappedNBTTagCompound wrappedTag = COMPOUND_TAG.get().setHandle(item);
		boolean b = wrappedTag.getBoolean(key);
		wrappedTag.clear();
		return b;
	}
	
	public static byte getByte(ItemStack item, String key) {
		if(!exists(item) || !item.hasItemMeta()) return 0;
		WrappedNBTTagCompound wrappedTag = COMPOUND_TAG.get().setHandle(item);
		byte b = wrappedTag.getByte(key);
		wrappedTag.clear();
		return b;
	}
	
	public static long getLong(ItemStack item, String key) {
		if(!exists(item) || !item.hasItemMeta()) return 0;
		WrappedNBTTagCompound wrappedTag = COMPOUND_TAG.get().setHandle(item);
		long l = wrappedTag.getLong(key);
		wrappedTag.clear();
		return l;
	}
	
	public static short getShort(ItemStack item, String key) {
		if(!exists(item) || !item.hasItemMeta()) return 0;
		WrappedNBTTagCompound wrappedTag = COMPOUND_TAG.get().setHandle(item);
		short s = wrappedTag.getShort(key);
		wrappedTag.clear();
		return s;
	}
	
	public static int[] getIntArray(ItemStack item, String key) {
		if(!exists(item) || !item.hasItemMeta()) return null;
		WrappedNBTTagCompound wrappedTag = COMPOUND_TAG.get().setHandle(item);
		int[] array = wrappedTag.getIntArray(key);
		wrappedTag.clear();
		return array;
	}
	
	public static byte[] getByteArray(ItemStack item, String key) {
		if(!exists(item) || !item.hasItemMeta()) return null;
		WrappedNBTTagCompound wrappedTag = COMPOUND_TAG.get().setHandle(item);
		byte[] array = wrappedTag.getByteArray(key);
		wrappedTag.clear();
		return array;
	}
	
}
