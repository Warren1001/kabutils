package com.kabryxis.kabutils.spigot.version.wrapper.entity.villager.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.entity.villager.WrappedEntityVillager;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipelist.impl.WrappedMerchantRecipeListv1_8_R3;
import net.minecraft.server.v1_8_R3.EntityVillager;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftVillager;
import org.bukkit.entity.Villager;

public class WrappedEntityVillagerv1_8_R3 extends WrappedEntityVillager {
	
	private final EntityVillager entityVillager;
	
	public WrappedEntityVillagerv1_8_R3(Villager villager) {
		this.entityVillager = ((CraftVillager)villager).getHandle();
	}
	
	@Override
	public Object getObject() {
		return entityVillager;
	}
	
	@Override
	public WrappedMerchantRecipeListv1_8_R3 getOffers() {
		return new WrappedMerchantRecipeListv1_8_R3(entityVillager.getOffers(null));
	}
	
}
