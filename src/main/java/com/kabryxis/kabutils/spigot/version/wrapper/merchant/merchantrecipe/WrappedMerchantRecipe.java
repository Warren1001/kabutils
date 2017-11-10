package com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe;

import com.kabryxis.kabutils.spigot.version.wrapper.Wrappable;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe.impl.WrappedMerchantRecipev1_10_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe.impl.WrappedMerchantRecipev1_11_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe.impl.WrappedMerchantRecipev1_12_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe.impl.WrappedMerchantRecipev1_8_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe.impl.WrappedMerchantRecipev1_8_R2;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe.impl.WrappedMerchantRecipev1_8_R3;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe.impl.WrappedMerchantRecipev1_9_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe.impl.WrappedMerchantRecipev1_9_R2;

public abstract class WrappedMerchantRecipe<T> extends Wrappable<T> {
	
	static { // include in maven shade plugin
		WrappedMerchantRecipev1_8_R1.class.getClass();
		WrappedMerchantRecipev1_8_R2.class.getClass();
		WrappedMerchantRecipev1_8_R3.class.getClass();
		WrappedMerchantRecipev1_9_R1.class.getClass();
		WrappedMerchantRecipev1_9_R2.class.getClass();
		WrappedMerchantRecipev1_10_R1.class.getClass();
		WrappedMerchantRecipev1_11_R1.class.getClass();
		WrappedMerchantRecipev1_12_R1.class.getClass();
	}
	
	public abstract void newInstance(Object buyItem1, Object buyItem2, Object buyItem3, int uses, int maxUses);
	
	public abstract Object handleGetBuyingItem1();
	
	public abstract Object handleGetBuyingItem2();
	
	public abstract boolean hasSecondItem();
	
	public abstract Object handleGetSellingItem();
	
	public abstract int getUses();
	
	public abstract int getMaxUses();
	
}
