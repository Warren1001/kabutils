package com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe;

import com.kabryxis.kabutils.spigot.version.Version;
import com.kabryxis.kabutils.spigot.version.wrapper.Wrappable;
import com.kabryxis.kabutils.spigot.version.wrapper.WrapperFactory;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe.impl.*;

public interface WrappedMerchantRecipe extends Wrappable {

	Class<WrappedMerchantRecipev1_8_R3> v1_8_R3 = WrappedMerchantRecipev1_8_R3.class;
	Class<WrappedMerchantRecipev1_9_R2> v1_9_R2 = WrappedMerchantRecipev1_9_R2.class;
	Class<WrappedMerchantRecipev1_10_R1> v1_10_R1 = WrappedMerchantRecipev1_10_R1.class;
	Class<WrappedMerchantRecipev1_11_R1> v1_11_R1 = WrappedMerchantRecipev1_11_R1.class;
	Class<WrappedMerchantRecipev1_12_R1> v1_12_R1 = WrappedMerchantRecipev1_12_R1.class;
	Class<WrappedMerchantRecipev1_13_R2> v1_13_R2 = WrappedMerchantRecipev1_13_R2.class;
	Class<WrappedMerchantRecipev1_14_R1> v1_14_R1 = WrappedMerchantRecipev1_14_R1.class;
	Class<WrappedMerchantRecipev1_15_R1> v1_15_R1 = WrappedMerchantRecipev1_15_R1.class;
	Class<?> NMS_ITEMSTACK = Version.getNMSClass("ItemStack");
	
	static WrappedMerchantRecipe newInstance(Object buyItem1, Object buyItem2, Object sellingItem, int uses, int maxUses) {
		return WrapperFactory.getSupplier(WrappedMerchantRecipe.class, NMS_ITEMSTACK, NMS_ITEMSTACK, NMS_ITEMSTACK, int.class, int.class).apply(buyItem1, buyItem2, sellingItem, uses, maxUses);
	}
	
	@Override
	WrappedMerchantRecipe setHandle(Object obj);
	
	Object getBuyingItem1();
	
	Object getBuyingItem2();
	
	boolean hasSecondItem();
	
	Object getSellingItem();
	
	int getUses();
	
	int getMaxUses();
	
}
