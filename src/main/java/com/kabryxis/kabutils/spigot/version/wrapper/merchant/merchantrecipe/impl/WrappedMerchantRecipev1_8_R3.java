package com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe.WrappedMerchantRecipe;

import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.MerchantRecipe;

public class WrappedMerchantRecipev1_8_R3 implements WrappedMerchantRecipe {
	
	private MerchantRecipe merchantRecipe;
	
	public WrappedMerchantRecipev1_8_R3(Object[] objects) {
		setHandle(objects);
	}
	
	public WrappedMerchantRecipev1_8_R3(MerchantRecipe merchantRecipe) {
		this.merchantRecipe = merchantRecipe;
	}
	
	@Override
	public void setHandle(Object obj) {
		if(obj instanceof MerchantRecipe) merchantRecipe = (MerchantRecipe)obj;
		else if(obj instanceof Object[]) {
			Object[] objects = (Object[])obj;
			Object buyItem2 = objects[1];
			merchantRecipe = new MerchantRecipe((ItemStack)objects[0], buyItem2 != null ? (ItemStack)buyItem2 : null, (ItemStack)objects[2], (Integer)objects[3], (Integer)objects[4]);
		}
	}
	
	@Override
	public MerchantRecipe getHandle() {
		return merchantRecipe;
	}
	
	@Override
	public ItemStack getBuyingItem1() {
		return merchantRecipe.getBuyItem1();
	}
	
	@Override
	public boolean hasSecondItem() {
		return merchantRecipe.hasSecondItem();
	}
	
	@Override
	public ItemStack getBuyingItem2() {
		return merchantRecipe.getBuyItem2();
	}
	
	@Override
	public ItemStack getSellingItem() {
		return merchantRecipe.getBuyItem3();
	}
	
	@Override
	public int getUses() {
		return merchantRecipe.k().getInt("uses");
	}
	
	@Override
	public int getMaxUses() {
		return merchantRecipe.k().getInt("maxUses");
	}
	
}
