package com.kabryxis.kabutils.spigot.version.wrapper.item.itemstack.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.item.itemstack.WrappedItemStack;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.WrappedNBTTagCompound;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.impl.WrappedNBTTagCompoundv1_9_R2;
import net.minecraft.server.v1_9_R2.ItemStack;
import net.minecraft.server.v1_9_R2.NBTTagCompound;
import org.bukkit.craftbukkit.v1_9_R2.inventory.CraftItemStack;

public class WrappedItemStackv1_9_R2 implements WrappedItemStack {
	
	private ItemStack itemStack;
	private boolean clone = false;
	
	public WrappedItemStackv1_9_R2(Object obj) {
		setHandle(obj);
	}
	
	@Override
	public WrappedItemStackv1_9_R2 setHandle(Object obj) {
		if(obj instanceof ItemStack) this.itemStack = (ItemStack)obj;
		else if(obj instanceof CraftItemStack) {
			try {
				this.itemStack = (ItemStack)WrappedItemStack.FIELD_HANDLE.get(obj);
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
		return this;
	}
	
	@Override
	public ItemStack getHandle() {
		return itemStack;
	}
	
	@Override
	public void clear() {
		itemStack = null;
		clone = false;
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
