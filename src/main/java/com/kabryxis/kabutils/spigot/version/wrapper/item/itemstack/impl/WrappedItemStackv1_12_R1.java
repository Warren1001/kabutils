package com.kabryxis.kabutils.spigot.version.wrapper.item.itemstack.impl;

import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;

import com.kabryxis.kabutils.spigot.version.wrapper.item.itemstack.WrappedItemStack;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.WrappedNBTTagCompound;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.impl.WrappedNBTTagCompoundv1_12_R1;

import net.minecraft.server.v1_12_R1.ItemStack;

public class WrappedItemStackv1_12_R1 extends WrappedItemStack {
	
	private final ItemStack itemStack;
	
	public WrappedItemStackv1_12_R1(org.bukkit.inventory.ItemStack itemStack) {
		this.itemStack = CraftItemStack.asNMSCopy(itemStack);
	}
	
	public WrappedItemStackv1_12_R1(WrappedNBTTagCompound tag) {
		this.itemStack = new ItemStack(((WrappedNBTTagCompoundv1_12_R1)tag).getHandle());
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
		itemStack.save(((WrappedNBTTagCompoundv1_12_R1)tag).getHandle());
	}
	
}
