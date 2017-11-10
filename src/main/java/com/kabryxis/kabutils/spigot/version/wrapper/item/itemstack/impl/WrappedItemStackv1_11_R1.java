package com.kabryxis.kabutils.spigot.version.wrapper.item.itemstack.impl;

import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;

import com.kabryxis.kabutils.spigot.version.wrapper.item.itemstack.WrappedItemStack;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.WrappedNBTTagCompound;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.impl.WrappedNBTTagCompoundv1_11_R1;

import net.minecraft.server.v1_11_R1.ItemStack;

public class WrappedItemStackv1_11_R1 extends WrappedItemStack<ItemStack> {
	
	@Override
	public void createStack(WrappedNBTTagCompound<?> tag) {
		set(new ItemStack(((WrappedNBTTagCompoundv1_11_R1)tag).get()));
	}
	
	@Override
	public void setBukkitItemStack(org.bukkit.inventory.ItemStack item) {
		set(CraftItemStack.asNMSCopy(item));
	}
	
	@Override
	public org.bukkit.inventory.ItemStack getBukkitItemStack() {
		return CraftItemStack.asBukkitCopy(get());
	}
	
	@Override
	public void save(WrappedNBTTagCompound<?> tag) {
		get().save(((WrappedNBTTagCompoundv1_11_R1)tag).get());
	}
	
}
