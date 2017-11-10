package com.kabryxis.kabutils.spigot.version.wrapper.entity.villager;

import org.bukkit.entity.Villager;

import com.kabryxis.kabutils.spigot.version.wrapper.Wrappable;
import com.kabryxis.kabutils.spigot.version.wrapper.entity.villager.impl.WrappedEntityVillagerv1_10_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.entity.villager.impl.WrappedEntityVillagerv1_11_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.entity.villager.impl.WrappedEntityVillagerv1_12_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.entity.villager.impl.WrappedEntityVillagerv1_8_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.entity.villager.impl.WrappedEntityVillagerv1_8_R2;
import com.kabryxis.kabutils.spigot.version.wrapper.entity.villager.impl.WrappedEntityVillagerv1_8_R3;
import com.kabryxis.kabutils.spigot.version.wrapper.entity.villager.impl.WrappedEntityVillagerv1_9_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.entity.villager.impl.WrappedEntityVillagerv1_9_R2;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipelist.WrappedMerchantRecipeList;

public abstract class WrappedEntityVillager<T> extends Wrappable<T> {
	
	static { // include in maven shade plugin
		WrappedEntityVillagerv1_8_R1.class.getClass();
		WrappedEntityVillagerv1_8_R2.class.getClass();
		WrappedEntityVillagerv1_8_R3.class.getClass();
		WrappedEntityVillagerv1_9_R1.class.getClass();
		WrappedEntityVillagerv1_9_R2.class.getClass();
		WrappedEntityVillagerv1_10_R1.class.getClass();
		WrappedEntityVillagerv1_11_R1.class.getClass();
		WrappedEntityVillagerv1_12_R1.class.getClass();
	}
	
	public abstract void setVillager(Villager villager);
	
	public abstract WrappedMerchantRecipeList<?> getOffers();
	
}
