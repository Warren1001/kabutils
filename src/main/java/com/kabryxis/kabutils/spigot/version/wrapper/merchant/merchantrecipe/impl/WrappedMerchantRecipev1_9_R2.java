package com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe.WrappedMerchantRecipe;

import net.minecraft.server.v1_9_R2.ItemStack;
import net.minecraft.server.v1_9_R2.MerchantRecipe;

public class WrappedMerchantRecipev1_9_R2 extends WrappedMerchantRecipe<MerchantRecipe> {
	
	@Override
	public void newInstance(Object buyItem1, Object buyItem2, Object buyItem3, int uses, int maxUses) {
		set(new MerchantRecipe((ItemStack)buyItem1, buyItem2 != null ? (ItemStack)buyItem2 : null, (ItemStack)buyItem3, uses, maxUses));
	}
	
	@Override
	public Object handleGetBuyingItem1() {
		return get().getBuyItem1();
	}
	
	@Override
	public boolean hasSecondItem() {
		return get().hasSecondItem();
	}
	
	@Override
	public Object handleGetBuyingItem2() {
		return get().getBuyItem2();
	}
	
	@Override
	public Object handleGetSellingItem() {
		return get().getBuyItem3();
	}
	
	@Override
	public int getUses() {
		return get().k().getInt("uses");
	}
	
	@Override
	public int getMaxUses() {
		return get().k().getInt("maxUses");
	}
	
}
