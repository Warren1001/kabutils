package com.kabryxis.kabutils.spigot.version.wrapper.entity.villager;

import com.kabryxis.kabutils.spigot.version.wrapper.Wrappable;
import com.kabryxis.kabutils.spigot.version.wrapper.WrapperFactory;
import com.kabryxis.kabutils.spigot.version.wrapper.entity.villager.impl.*;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipelist.WrappedMerchantRecipeList;
import org.bukkit.entity.Villager;

public interface WrappedEntityVillager extends Wrappable {

	Class<WrappedEntityVillagerv1_8_R3> v1_8_R3 = WrappedEntityVillagerv1_8_R3.class;
	Class<WrappedEntityVillagerv1_9_R2> v1_9_R2 = WrappedEntityVillagerv1_9_R2.class;
	Class<WrappedEntityVillagerv1_10_R1> v1_10_R1 = WrappedEntityVillagerv1_10_R1.class;
	Class<WrappedEntityVillagerv1_11_R1> v1_11_R1 = WrappedEntityVillagerv1_11_R1.class;
	Class<WrappedEntityVillagerv1_12_R1> v1_12_R1 = WrappedEntityVillagerv1_12_R1.class;
	Class<WrappedEntityVillagerv1_13_R2> v1_13_R2 = WrappedEntityVillagerv1_13_R2.class;
	Class<WrappedEntityVillagerv1_14_R1> v1_14_R1 = WrappedEntityVillagerv1_14_R1.class;
	Class<WrappedEntityVillagerv1_15_R1> v1_15_R1 = WrappedEntityVillagerv1_15_R1.class;
	
	static WrappedEntityVillager newInstance(Villager villager) {
		return WrapperFactory.getSupplier(WrappedEntityVillager.class, Villager.class).apply(villager);
	}
	
	@Override
	WrappedEntityVillager setHandle(Object obj);
	
	WrappedMerchantRecipeList getOffers();
	
}
