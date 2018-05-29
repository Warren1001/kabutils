package com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe.WrappedMerchantRecipe;

import net.minecraft.server.v1_9_R1.ItemStack;
import net.minecraft.server.v1_9_R1.MerchantRecipe;

public class WrappedMerchantRecipev1_9_R1 extends WrappedMerchantRecipe {
	
	private final MerchantRecipe merchantRecipe;
	
	public WrappedMerchantRecipev1_9_R1(Object[] objects) {
		Object buyItem2 = objects[1];
		this.merchantRecipe = new MerchantRecipe((ItemStack)objects[0], buyItem2 != null ? (ItemStack)buyItem2 : null, (ItemStack)objects[2], (Integer)objects[3], (Integer)objects[4]);
	}
	
	public WrappedMerchantRecipev1_9_R1(Object handle) {
		this.merchantRecipe = (MerchantRecipe)handle;
	}
	
	public MerchantRecipe getHandle() {
		return merchantRecipe;
	}
	
	@Override
	public Object getObject() {
		return merchantRecipe;
	}
	
	@Override
	public Object handleGetBuyingItem1() {
		return merchantRecipe.getBuyItem1();
	}
	
	@Override
	public boolean hasSecondItem() {
		return merchantRecipe.hasSecondItem();
	}
	
	@Override
	public Object handleGetBuyingItem2() {
		return merchantRecipe.getBuyItem2();
	}
	
	@Override
	public Object handleGetSellingItem() {
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
