package com.kabryxis.kabutils.spigot.version.wrapper.item.itemstack.impl;

import com.kabryxis.kabutils.spigot.version.Version;
import com.kabryxis.kabutils.spigot.version.wrapper.item.itemstack.WrappedItemStack;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.WrappedNBTTagCompound;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.impl.WrappedNBTTagCompoundv1_9_R2;
import net.minecraft.server.v1_9_R2.ItemStack;
import net.minecraft.server.v1_9_R2.NBTTagCompound;
import org.bukkit.craftbukkit.v1_9_R2.inventory.CraftItemStack;

import java.lang.reflect.Field;

public class WrappedItemStackv1_9_R2 implements WrappedItemStack {
	
	private static final Field field;
	
	static {
		Field localField = null;
		try {
			localField = Version.getOBCClass("inventory.CraftItemStack").getDeclaredField("handle");
			localField.setAccessible(true);
		} catch(NoSuchFieldException e) {
			e.printStackTrace();
		}
		field = localField;
	}
	
	private ItemStack itemStack;
	private boolean clone = false;
	
	public WrappedItemStackv1_9_R2(Object obj) {
		if(obj != null) setHandle(obj);
	}
	
	@Override
	public void setHandle(Object obj) {
		if(obj instanceof ItemStack) this.itemStack = (ItemStack)obj;
		else if(obj instanceof CraftItemStack) {
			try {
				this.itemStack = (ItemStack)field.get(obj);
			} catch(IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		else if(obj instanceof org.bukkit.inventory.ItemStack) {
			clone = true;
			this.itemStack = CraftItemStack.asNMSCopy((org.bukkit.inventory.ItemStack)obj);
		}
		else if(obj instanceof WrappedNBTTagCompoundv1_9_R2) {
			clone = true;
			this.itemStack = ItemStack.createStack(((WrappedNBTTagCompoundv1_9_R2)obj).getHandle());
		}
		else if(obj == null) itemStack = null;
	}
	
	@Override
	public ItemStack getHandle() {
		return itemStack;
	}
	
	@Override
	public boolean isClone() {
		return clone;
	}
	
	@Override
	public CraftItemStack getBukkitItemStack() {
		return CraftItemStack.asCraftMirror(itemStack);
	}
	
	@Override
	public WrappedNBTTagCompoundv1_9_R2 getTag(boolean create) {
		NBTTagCompound tag = itemStack.getTag();
		if(tag == null) {
			if(!create) return null;
			tag = new NBTTagCompound();
			itemStack.setTag(tag);
		}
		return new WrappedNBTTagCompoundv1_9_R2(tag);
	}
	
	@Override
	public void save(WrappedNBTTagCompound tag) {
		itemStack.save(((WrappedNBTTagCompoundv1_9_R2)tag).getHandle());
	}
	
}
