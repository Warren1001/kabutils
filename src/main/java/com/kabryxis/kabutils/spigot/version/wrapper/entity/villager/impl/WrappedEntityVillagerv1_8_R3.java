package com.kabryxis.kabutils.spigot.version.wrapper.entity.villager.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.entity.villager.WrappedEntityVillager;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipelist.impl.WrappedMerchantRecipeListv1_8_R3;
import net.minecraft.server.v1_8_R3.EntityVillager;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftVillager;
import org.bukkit.entity.Villager;

public class WrappedEntityVillagerv1_8_R3 implements WrappedEntityVillager {
	
	private EntityVillager entityVillager;
	
	public WrappedEntityVillagerv1_8_R3(Villager villager) {
		setHandle(villager);
	}
	
	@Override
	public WrappedEntityVillagerv1_8_R3 setHandle(Object obj) {
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
	public WrappedMerchantRecipeListv1_8_R3 getOffers() {
		return new WrappedMerchantRecipeListv1_8_R3(entityVillager.getOffers(null));
	}
	
}
