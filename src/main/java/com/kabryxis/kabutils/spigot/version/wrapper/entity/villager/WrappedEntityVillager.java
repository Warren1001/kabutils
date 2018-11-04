package com.kabryxis.kabutils.spigot.version.wrapper.entity.villager;

import com.kabryxis.kabutils.spigot.version.wrapper.Wrappable;
import com.kabryxis.kabutils.spigot.version.wrapper.WrapperFactory;
import com.kabryxis.kabutils.spigot.version.wrapper.entity.villager.impl.*;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipelist.WrappedMerchantRecipeList;
import org.bukkit.entity.Villager;

public interface WrappedEntityVillager extends Wrappable {
	
	Class<WrappedEntityVillagerv1_8_R1> v1_8_R1 = WrappedEntityVillagerv1_8_R1.class;
	Class<WrappedEntityVillagerv1_8_R2> v1_8_R2 = WrappedEntityVillagerv1_8_R2.class;
	Class<WrappedEntityVillagerv1_8_R3> v1_8_R3 = WrappedEntityVillagerv1_8_R3.class;
	Class<WrappedEntityVillagerv1_9_R1> v1_9_R1 = WrappedEntityVillagerv1_9_R1.class;
	Class<WrappedEntityVillagerv1_9_R2> v1_9_R2 = WrappedEntityVillagerv1_9_R2.class;
	Class<WrappedEntityVillagerv1_10_R1> v1_10_R1 = WrappedEntityVillagerv1_10_R1.class;
	Class<WrappedEntityVillagerv1_11_R1> v1_11_R1 = WrappedEntityVillagerv1_11_R1.class;
	Class<WrappedEntityVillagerv1_12_R1> v1_12_R1 = WrappedEntityVillagerv1_12_R1.class;
	
	static WrappedEntityVillager newInstance(Villager villager) {
		return WrapperFactory.get(WrappedEntityVillager.class, new Class[] { Villager.class }, new Object[] { villager });
	}
	
	WrappedMerchantRecipeList getOffers();
	
}
