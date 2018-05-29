package com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipelist;

import com.kabryxis.kabutils.spigot.version.Version;
import com.kabryxis.kabutils.spigot.version.wrapper.Wrappable;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe.WrappedMerchantRecipe;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipelist.impl.*;

import java.util.List;
import java.util.function.Supplier;

public abstract class WrappedMerchantRecipeList implements Wrappable {
	
	private static final Supplier<WrappedMerchantRecipeList> supplier;
	
	static {
		switch(Version.VERSION) {
			case v1_8_R1:
				supplier = WrappedMerchantRecipeListv1_8_R1::new;
				break;
			case v1_8_R2:
				supplier = WrappedMerchantRecipeListv1_8_R2::new;
				break;
			case v1_8_R3:
				supplier = WrappedMerchantRecipeListv1_8_R3::new;
				break;
			case v1_9_R1:
				supplier = WrappedMerchantRecipeListv1_9_R1::new;
				break;
			case v1_9_R2:
				supplier = WrappedMerchantRecipeListv1_9_R2::new;
				break;
			case v1_10_R1:
				supplier = WrappedMerchantRecipeListv1_10_R1::new;
				break;
			case v1_11_R1:
				supplier = WrappedMerchantRecipeListv1_11_R1::new;
				break;
			case v1_12_R1:
				supplier = WrappedMerchantRecipeListv1_12_R1::new;
				break;
			default:
				supplier = null;
				break;
		}
	}
	
	public static WrappedMerchantRecipeList newInstance() {
		return supplier.get();
	}
	
	public abstract List<WrappedMerchantRecipe> getRecipes();
	
	public abstract void setRecipes(List<WrappedMerchantRecipe> recipes);
	
}
