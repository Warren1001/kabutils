package com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe;

import com.kabryxis.kabutils.spigot.version.Version;
import com.kabryxis.kabutils.spigot.version.wrapper.Wrappable;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe.impl.*;

import java.util.function.Function;

public abstract class WrappedMerchantRecipe implements Wrappable {
	
	private static final Function<Object[], WrappedMerchantRecipe> rawSupplier;
	//private static final Function<Object, WrappedMerchantRecipe> handleSupplier;
	
	static {
		switch(Version.VERSION) {
			case v1_8_R1:
				rawSupplier = WrappedMerchantRecipev1_8_R1::new;
				//handleSupplier = WrappedMerchantRecipev1_8_R1::new;
				break;
			case v1_8_R2:
				rawSupplier = WrappedMerchantRecipev1_8_R2::new;
				//handleSupplier = WrappedMerchantRecipev1_8_R2::new;
				break;
			case v1_8_R3:
				rawSupplier = WrappedMerchantRecipev1_8_R3::new;
				//handleSupplier = WrappedMerchantRecipev1_8_R3::new;
				break;
			case v1_9_R1:
				rawSupplier = WrappedMerchantRecipev1_9_R1::new;
				//handleSupplier = WrappedMerchantRecipev1_9_R1::new;
				break;
			case v1_9_R2:
				rawSupplier = WrappedMerchantRecipev1_9_R2::new;
				//handleSupplier = WrappedMerchantRecipev1_9_R2::new;
				break;
			case v1_10_R1:
				rawSupplier = WrappedMerchantRecipev1_10_R1::new;
				//handleSupplier = WrappedMerchantRecipev1_10_R1::new;
				break;
			case v1_11_R1:
				rawSupplier = WrappedMerchantRecipev1_11_R1::new;
				//handleSupplier = WrappedMerchantRecipev1_11_R1::new;
				break;
			case v1_12_R1:
				rawSupplier = WrappedMerchantRecipev1_12_R1::new;
				//handleSupplier = WrappedMerchantRecipev1_12_R1::new;
				break;
			default:
				rawSupplier = null;
				//handleSupplier = null;
				break;
		}
	}
	
	public static WrappedMerchantRecipe newInstance(Object buyItem1, Object buyItem2, Object buyItem3, int uses, int maxUses) {
		return rawSupplier.apply(new Object[] {buyItem1, buyItem2, buyItem3, uses, maxUses});
	}
	
	/*public static WrappedMerchantRecipe newInstance(Object handle) {
		return handleSupplier.apply(handle);
	}*/
	
	public abstract Object handleGetBuyingItem1();
	
	public abstract Object handleGetBuyingItem2();
	
	public abstract boolean hasSecondItem();
	
	public abstract Object handleGetSellingItem();
	
	public abstract int getUses();
	
	public abstract int getMaxUses();
	
}
