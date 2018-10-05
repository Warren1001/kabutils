package com.kabryxis.kabutils.spigot.version.wrapper.item.itemstack;

import com.kabryxis.kabutils.spigot.version.wrapper.Wrappable;
import com.kabryxis.kabutils.spigot.version.wrapper.WrapperFactory;
import com.kabryxis.kabutils.spigot.version.wrapper.item.itemstack.impl.*;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.WrappedNBTTagCompound;
import org.bukkit.inventory.ItemStack;

public interface WrappedItemStack extends Wrappable {
	
	Class<WrappedItemStackv1_8_R1> v1_8_R1 = WrappedItemStackv1_8_R1.class;
	Class<WrappedItemStackv1_8_R2> v1_8_R2 = WrappedItemStackv1_8_R2.class;
	Class<WrappedItemStackv1_8_R3> v1_8_R3 = WrappedItemStackv1_8_R3.class;
	Class<WrappedItemStackv1_9_R1> v1_9_R1 = WrappedItemStackv1_9_R1.class;
	Class<WrappedItemStackv1_9_R2> v1_9_R2 = WrappedItemStackv1_9_R2.class;
	Class<WrappedItemStackv1_10_R1> v1_10_R1 = WrappedItemStackv1_10_R1.class;
	Class<WrappedItemStackv1_11_R1> v1_11_R1 = WrappedItemStackv1_11_R1.class;
	Class<WrappedItemStackv1_12_R1> v1_12_R1 = WrappedItemStackv1_12_R1.class;
	
	static WrappedItemStack newInstance() {
		return WrapperFactory.getSupplier(WrappedItemStack.class, Object.class).apply(null);
	}
	
	static WrappedItemStack newInstance(ItemStack itemStack) {
		return WrapperFactory.getSupplier(WrappedItemStack.class, Object.class).apply(itemStack);
	}
	
	static WrappedItemStack newInstance(WrappedNBTTagCompound tag) {
		return WrapperFactory.getSupplier(WrappedItemStack.class, Object.class).apply(tag);
	}
	
	boolean isClone();
	
	ItemStack getBukkitItemStack();
	
	WrappedNBTTagCompound getTag(boolean create);
	
	void save(WrappedNBTTagCompound tag);
	
}
