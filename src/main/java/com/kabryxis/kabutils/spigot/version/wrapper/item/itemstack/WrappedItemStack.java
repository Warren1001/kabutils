package com.kabryxis.kabutils.spigot.version.wrapper.item.itemstack;

import org.bukkit.inventory.ItemStack;

import com.kabryxis.kabutils.spigot.version.wrapper.Wrappable;
import com.kabryxis.kabutils.spigot.version.wrapper.item.itemstack.impl.WrappedItemStackv1_10_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.item.itemstack.impl.WrappedItemStackv1_11_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.item.itemstack.impl.WrappedItemStackv1_12_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.item.itemstack.impl.WrappedItemStackv1_8_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.item.itemstack.impl.WrappedItemStackv1_8_R2;
import com.kabryxis.kabutils.spigot.version.wrapper.item.itemstack.impl.WrappedItemStackv1_8_R3;
import com.kabryxis.kabutils.spigot.version.wrapper.item.itemstack.impl.WrappedItemStackv1_9_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.item.itemstack.impl.WrappedItemStackv1_9_R2;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.WrappedNBTTagCompound;

public abstract class WrappedItemStack<T> extends Wrappable<T> {
	
	static { // include in maven shade plugin
		WrappedItemStackv1_8_R1.class.getClass();
		WrappedItemStackv1_8_R2.class.getClass();
		WrappedItemStackv1_8_R3.class.getClass();
		WrappedItemStackv1_9_R1.class.getClass();
		WrappedItemStackv1_9_R2.class.getClass();
		WrappedItemStackv1_10_R1.class.getClass();
		WrappedItemStackv1_11_R1.class.getClass();
		WrappedItemStackv1_12_R1.class.getClass();
	}
	
	public abstract void createStack(WrappedNBTTagCompound<?> tag);
	
	public abstract void setBukkitItemStack(ItemStack item);
	
	public abstract ItemStack getBukkitItemStack();
	
	public abstract void save(WrappedNBTTagCompound<?> tag);
	
}
