package com.kabryxis.kabutils.spigot.version.wrapper.entity.villager.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.entity.villager.WrappedEntityVillager;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipelist.impl.WrappedMerchantRecipeListv1_9_R2;
import net.minecraft.server.v1_9_R2.EntityVillager;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftVillager;
import org.bukkit.entity.Villager;

public class WrappedEntityVillagerv1_9_R2 extends WrappedEntityVillager {
	
	private final EntityVillager entityVillager;
	
	public WrappedEntityVillagerv1_9_R2(Villager villager) {
		this.entityVillager = ((CraftVillager)villager).getHandle();
	}
	
	@Override
	public Object getObject() {
		return entityVillager;
	}
	
	@Override
	public WrappedMerchantRecipeListv1_9_R2 getOffers() {
		return new WrappedMerchantRecipeListv1_9_R2(entityVillager.getOffers(null));
	}
	
}
