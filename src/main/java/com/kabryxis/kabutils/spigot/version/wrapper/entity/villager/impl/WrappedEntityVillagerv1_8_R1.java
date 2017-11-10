package com.kabryxis.kabutils.spigot.version.wrapper.entity.villager.impl;

import org.bukkit.craftbukkit.v1_8_R1.entity.CraftVillager;
import org.bukkit.entity.Villager;

import com.kabryxis.kabutils.spigot.version.wrapper.WrapperCache;
import com.kabryxis.kabutils.spigot.version.wrapper.entity.villager.WrappedEntityVillager;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipelist.WrappedMerchantRecipeList;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipelist.impl.WrappedMerchantRecipeListv1_8_R1;

import net.minecraft.server.v1_8_R1.EntityVillager;

public class WrappedEntityVillagerv1_8_R1 extends WrappedEntityVillager<EntityVillager> {
	
	@Override
	public void setVillager(Villager villager) {
		set(((CraftVillager)villager).getHandle());
	}
	
	@Override
	public WrappedMerchantRecipeListv1_8_R1 getOffers() {
		WrappedMerchantRecipeListv1_8_R1 handle = (WrappedMerchantRecipeListv1_8_R1)WrapperCache.get(WrappedMerchantRecipeList.class);
		handle.set(get().getOffers(null));
		return handle;
	}
	
}
