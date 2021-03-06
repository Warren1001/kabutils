package com.kabryxis.kabutils.spigot.version.wrapper.entity.villager.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.entity.villager.WrappedEntityVillager;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipelist.impl.WrappedMerchantRecipeListv1_15_R1;
import net.minecraft.server.v1_15_R1.EntityVillager;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftVillager;
import org.bukkit.entity.Villager;

public class WrappedEntityVillagerv1_15_R1 implements WrappedEntityVillager {
	
	private EntityVillager entityVillager;
	
	public WrappedEntityVillagerv1_15_R1(Villager villager) {
		setHandle(villager);
	}
	
	@Override
	public WrappedEntityVillagerv1_15_R1 setHandle(Object obj) {
		if(obj instanceof EntityVillager) entityVillager = (EntityVillager)obj;
		else if(obj instanceof Villager) entityVillager = ((CraftVillager)obj).getHandle();
		return this;
	}
	
	@Override
	public EntityVillager getHandle() {
		return entityVillager;
	}
	
	@Override
	public void clear() {
		entityVillager = null;
	}
	
	@Override
	public WrappedMerchantRecipeListv1_15_R1 getOffers() {
		return new WrappedMerchantRecipeListv1_15_R1(entityVillager.getOffers());
	}
	
}