package com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe;

import com.kabryxis.kabutils.spigot.version.wrapper.Wrappable;
import com.kabryxis.kabutils.spigot.version.wrapper.WrapperFactory;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe.impl.*;

public interface WrappedMerchantRecipe extends Wrappable {
	
	Class<WrappedMerchantRecipev1_8_R1> v1_8_R1 = WrappedMerchantRecipev1_8_R1.class;
	Class<WrappedMerchantRecipev1_8_R2> v1_8_R2 = WrappedMerchantRecipev1_8_R2.class;
	Class<WrappedMerchantRecipev1_8_R3> v1_8_R3 = WrappedMerchantRecipev1_8_R3.class;
	Class<WrappedMerchantRecipev1_9_R1> v1_9_R1 = WrappedMerchantRecipev1_9_R1.class;
	Class<WrappedMerchantRecipev1_9_R2> v1_9_R2 = WrappedMerchantRecipev1_9_R2.class;
	Class<WrappedMerchantRecipev1_10_R1> v1_10_R1 = WrappedMerchantRecipev1_10_R1.class;
	Class<WrappedMerchantRecipev1_11_R1> v1_11_R1 = WrappedMerchantRecipev1_11_R1.class;
	Class<WrappedMerchantRecipev1_12_R1> v1_12_R1 = WrappedMerchantRecipev1_12_R1.class;
	
	static WrappedMerchantRecipe newInstance(Object buyItem1, Object buyItem2, Object buyItem3, int uses, int maxUses) {
		return WrapperFactory.get(WrappedMerchantRecipe.class, new Class[] { Object[].class }, new Object[] { new Object[] {buyItem1, buyItem2, buyItem3, uses, maxUses} });
	}
	
	Object getBuyingItem1();
	
	Object getBuyingItem2();
	
	boolean hasSecondItem();
	
	Object getSellingItem();
	
	int getUses();
	
	int getMaxUses();
	
}
