package com.kabryxis.kabutils.spigot.version.wrapper.item.itemstack;

import com.kabryxis.kabutils.spigot.version.Version;
import com.kabryxis.kabutils.spigot.version.wrapper.Wrappable;
import com.kabryxis.kabutils.spigot.version.wrapper.item.itemstack.impl.*;
import com.kabryxis.kabutils.spigot.version.wrapper.nbt.compound.WrappedNBTTagCompound;
import org.bukkit.inventory.ItemStack;

import java.util.function.Function;

public abstract class WrappedItemStack implements Wrappable {
	
	private static final Function<ItemStack, WrappedItemStack> bukkitSupplier;
	private static final Function<WrappedNBTTagCompound, WrappedItemStack> tagSupplier;
	
	static {
		switch(Version.VERSION) {
			case v1_8_R1:
				bukkitSupplier = WrappedItemStackv1_8_R1::new;
				tagSupplier = WrappedItemStackv1_8_R1::new;
				break;
			case v1_8_R2:
				bukkitSupplier = WrappedItemStackv1_8_R2::new;
				tagSupplier = WrappedItemStackv1_8_R2::new;
				break;
			case v1_8_R3:
				bukkitSupplier = WrappedItemStackv1_8_R3::new;
				tagSupplier = WrappedItemStackv1_8_R3::new;
				break;
			case v1_9_R1:
				bukkitSupplier = WrappedItemStackv1_9_R1::new;
				tagSupplier = WrappedItemStackv1_9_R1::new;
				break;
			case v1_9_R2:
				bukkitSupplier = WrappedItemStackv1_9_R2::new;
				tagSupplier = WrappedItemStackv1_9_R2::new;
				break;
			case v1_10_R1:
				bukkitSupplier = WrappedItemStackv1_10_R1::new;
				tagSupplier = WrappedItemStackv1_10_R1::new;
				break;
			case v1_11_R1:
				bukkitSupplier = WrappedItemStackv1_11_R1::new;
				tagSupplier = WrappedItemStackv1_11_R1::new;
				break;
			case v1_12_R1:
				bukkitSupplier = WrappedItemStackv1_12_R1::new;
				tagSupplier = WrappedItemStackv1_12_R1::new;
				break;
			default:
				bukkitSupplier = null;
				tagSupplier = null;
				break;
		}
	}
	
	public static WrappedItemStack newInstance(ItemStack itemStack) {
		return bukkitSupplier.apply(itemStack);
	}
	
	public static WrappedItemStack newInstance(WrappedNBTTagCompound tag) {
		return tagSupplier.apply(tag);
	}
	
	public abstract ItemStack getBukkitItemStack();
	
	public abstract void save(WrappedNBTTagCompound tag);
	
}
