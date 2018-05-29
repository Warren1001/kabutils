package com.kabryxis.kabutils.spigot.version.wrapper.entity.villager.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.entity.villager.WrappedEntityVillager;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipelist.impl.WrappedMerchantRecipeListv1_10_R1;
import net.minecraft.server.v1_10_R1.EntityVillager;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftVillager;
import org.bukkit.entity.Villager;

public class WrappedEntityVillagerv1_10_R1 extends WrappedEntityVillager {
	
	private final EntityVillager entityVillager;
	
	public WrappedEntityVillagerv1_10_R1(Villager villager) {
		this.entityVillager = ((CraftVillager)villager).getHandle();
	}
	
	@Override
	public Object getObject() {
		return entityVillager;
	}
	
	@Override
	public WrappedMerchantRecipeListv1_10_R1 getOffers() {
		return new WrappedMerchantRecipeListv1_10_R1(entityVillager.getOffers(null));
	}
	
}
