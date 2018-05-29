package com.kabryxis.kabutils.spigot.version.wrapper.item.itemstack.impl;

import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;

import com.kabryxis.kabutils.spigot.version.wrapper.item.itemstack.WrappedItemStack;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.WrappedNBTTagCompound;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.impl.WrappedNBTTagCompoundv1_8_R3;

import net.minecraft.server.v1_8_R3.ItemStack;

public class WrappedItemStackv1_8_R3 extends WrappedItemStack {
	
	private final ItemStack itemStack;
	
	public WrappedItemStackv1_8_R3(org.bukkit.inventory.ItemStack itemStack) {
		this.itemStack = CraftItemStack.asNMSCopy(itemStack);
	}
	
	public WrappedItemStackv1_8_R3(WrappedNBTTagCompound tag) {
		this.itemStack = ItemStack.createStack(((WrappedNBTTagCompoundv1_8_R3)tag).getHandle());
	}
	
	@Override
	public Object getObject() {
		return itemStack;
	}
	
	@Override
	public org.bukkit.inventory.ItemStack getBukkitItemStack() {
		return CraftItemStack.asBukkitCopy(itemStack);
	}
	
	@Override
	public void save(WrappedNBTTagCompound tag) {
		itemStack.save(((WrappedNBTTagCompoundv1_8_R3)tag).getHandle());
	}
	
}
