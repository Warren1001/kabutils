package com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe.WrappedMerchantRecipe;
import net.minecraft.server.v1_9_R2.ItemStack;
import net.minecraft.server.v1_9_R2.MerchantRecipe;

public class WrappedMerchantRecipev1_9_R2 implements WrappedMerchantRecipe {
	
	private MerchantRecipe merchantRecipe;
	
	public WrappedMerchantRecipev1_9_R2(ItemStack buyItem1, ItemStack sellingItem, int uses, int maxUses) {
		this(buyItem1, null, sellingItem, uses, maxUses);
	}
	
	public WrappedMerchantRecipev1_9_R2(ItemStack buyItem1, ItemStack buyItem2, ItemStack sellingItem, int uses, int maxUses) {
		this(new MerchantRecipe(buyItem1, buyItem2, sellingItem, uses, maxUses));
	}
	
	public WrappedMerchantRecipev1_9_R2(MerchantRecipe merchantRecipe) {
		this.merchantRecipe = merchantRecipe;
	}
	
	@Override
	public WrappedMerchantRecipev1_9_R2 setHandle(Object obj) {
		if(obj instanceof MerchantRecipe) merchantRecipe = (MerchantRecipe)obj;
		return this;
	}
	
	@Override
	public MerchantRecipe getHandle() {
		return merchantRecipe;
	}
	
	@Override
	public void clear() {
		merchantRecipe = null;
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
