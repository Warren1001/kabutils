package com.kabryxis.kabutils.spigot.version.wrapper.item.itemstack;

import com.kabryxis.kabutils.spigot.version.Version;
import com.kabryxis.kabutils.spigot.version.wrapper.Wrappable;
import com.kabryxis.kabutils.spigot.version.wrapper.WrapperFactory;
import com.kabryxis.kabutils.spigot.version.wrapper.item.itemstack.impl.*;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.WrappedNBTTagCompound;
import com.kabryxis.kabutils.utility.ReflectionHelper;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;

public interface WrappedItemStack extends Wrappable {
	
	Class<WrappedItemStackv1_8_R1> v1_8_R1 = WrappedItemStackv1_8_R1.class;
	Class<WrappedItemStackv1_8_R2> v1_8_R2 = WrappedItemStackv1_8_R2.class;
	Class<WrappedItemStackv1_8_R3> v1_8_R3 = WrappedItemStackv1_8_R3.class;
	Class<WrappedItemStackv1_9_R1> v1_9_R1 = WrappedItemStackv1_9_R1.class;
	Class<WrappedItemStackv1_9_R2> v1_9_R2 = WrappedItemStackv1_9_R2.class;
	Class<WrappedItemStackv1_10_R1> v1_10_R1 = WrappedItemStackv1_10_R1.class;
	Class<WrappedItemStackv1_11_R1> v1_11_R1 = WrappedItemStackv1_11_R1.class;
	Class<WrappedItemStackv1_12_R1> v1_12_R1 = WrappedItemStackv1_12_R1.class;
	Field FIELD_HANDLE = ReflectionHelper.getField(Version.getOBCClass("inventory.CraftItemStack"), "handle");
	
	static WrappedItemStack newInstance() {
		return WrapperFactory.getSupplier(WrappedItemStack.class, Object.class).apply((Object)null);
	}
	
	static WrappedItemStack newInstance(ItemStack itemStack) {
		return WrapperFactory.getSupplier(WrappedItemStack.class, Object.class).apply(itemStack);
	}
	
	static WrappedItemStack newInstance(WrappedNBTTagCompound tag) {
		return WrapperFactory.getSupplier(WrappedItemStack.class, Object.class).apply(tag);
	}
	
	@Override
	WrappedItemStack setHandle(Object obj);
	
	boolean isClone();
	
	ItemStack getBukkitItemStack();
	
	WrappedNBTTagCompound getTag(boolean create);
	
	void save(WrappedNBTTagCompound tag);
	
}
