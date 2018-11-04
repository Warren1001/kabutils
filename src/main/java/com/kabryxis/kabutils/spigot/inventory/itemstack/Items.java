package com.kabryxis.kabutils.spigot.inventory.itemstack;

import com.kabryxis.kabutils.spigot.version.wrapper.item.itemstack.WrappedItemStack;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.WrappedNBTTagCompound;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Items {
	
	private static final WrappedItemStack WRAPPED_ITEM_STACK = WrappedItemStack.newInstance();
	
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
	
	public static void setTagData(ItemStack item, String key, Object data) {
		Validate.isTrue(exists(item), "item cannot be null or air");
		WRAPPED_ITEM_STACK.setHandle(item);
		WRAPPED_ITEM_STACK.getTag(true).set(key, data);
		WRAPPED_ITEM_STACK.setHandle(null);
	}
	
	public static <T> T getTagData(ItemStack item, String key, Class<T> clazz) {
		if(!Items.exists(item)) return null;
		WRAPPED_ITEM_STACK.setHandle(item);
		WrappedNBTTagCompound tag = WRAPPED_ITEM_STACK.getTag(false);
		if(tag == null) return null;
		T obj = tag.get(key, clazz);
		WRAPPED_ITEM_STACK.setHandle(null);
		return obj;
	}
	
	public static <T> T getTagData(ItemStack item, String key, Class<T> clazz, T def) {
		T t = getTagData(item, key, clazz);
		if(t == null) t = def;
		return t;
	}
	
}
