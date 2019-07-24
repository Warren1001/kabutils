package com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe.WrappedMerchantRecipe;
import net.minecraft.server.v1_14_R1.ItemStack;
import net.minecraft.server.v1_14_R1.MerchantRecipe;

public class WrappedMerchantRecipev1_14_R1 implements WrappedMerchantRecipe {
	
	private MerchantRecipe merchantRecipe;
	
	public WrappedMerchantRecipev1_14_R1(ItemStack buyItem1, ItemStack sellingItem, int uses, int maxUses) {
		this(buyItem1, ItemStack.a, sellingItem, uses, maxUses);
	}
	
	public WrappedMerchantRecipev1_14_R1(ItemStack buyItem1, ItemStack buyItem2, ItemStack sellingItem, int uses, int maxUses) {
		this(new MerchantRecipe(buyItem1, buyItem2, sellingItem, uses, maxUses, 1.0F));
	}
	
	public WrappedMerchantRecipev1_14_R1(MerchantRecipe merchantRecipe) {
		this.merchantRecipe = merchantRecipe;
	}
	
	@Override
	public WrappedMerchantRecipev1_14_R1 setHandle(Object obj) {
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
		return merchantRecipe.buyingItem1;
	}
	
	@Override
	public boolean hasSecondItem() {
		return merchantRecipe.buyingItem2 != null;
	}
	
	@Override
	public ItemStack getBuyingItem2() {
		return merchantRecipe.buyingItem2;
	}
	
	@Override
	public ItemStack getSellingItem() {
		return merchantRecipe.sellingItem;
	}
	
	@Override
	public int getUses() {
		return merchantRecipe.uses;
	}
	
	@Override
	public int getMaxUses() {
		return merchantRecipe.maxUses;
	}
	
}
