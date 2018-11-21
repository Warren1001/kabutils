package com.kabryxis.kabutils.spigot.version.wrapper.item.itemstack.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.item.itemstack.WrappedItemStack;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.WrappedNBTTagCompound;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.impl.WrappedNBTTagCompoundv1_8_R3;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;

public class WrappedItemStackv1_8_R3 implements WrappedItemStack {
	
	private ItemStack itemStack;
	private boolean clone = false;
	
	public WrappedItemStackv1_8_R3(Object obj) {
		setHandle(obj);
	}
	
	@Override
	public WrappedItemStackv1_8_R3 setHandle(Object obj) {
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
		else if(obj instanceof WrappedNBTTagCompoundv1_8_R3) {
			clone = true;
			this.itemStack = ItemStack.createStack(((WrappedNBTTagCompoundv1_8_R3)obj).getHandle());
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
	public WrappedNBTTagCompoundv1_8_R3 getTag(boolean create) {
		NBTTagCompound tag = itemStack.getTag();
		if(tag == null) {
			if(!create) return null;
			tag = new NBTTagCompound();
			itemStack.setTag(tag);
		}
		return new WrappedNBTTagCompoundv1_8_R3(tag);
	}
	
	@Override
	public void save(WrappedNBTTagCompound tag) {
		itemStack.save(((WrappedNBTTagCompoundv1_8_R3)tag).getHandle());
	}
	
}
