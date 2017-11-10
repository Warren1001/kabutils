package com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipelist;

import java.util.List;

import com.kabryxis.kabutils.spigot.version.wrapper.Wrappable;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe.WrappedMerchantRecipe;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipelist.impl.WrappedMerchantRecipeListv1_10_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipelist.impl.WrappedMerchantRecipeListv1_11_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipelist.impl.WrappedMerchantRecipeListv1_12_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipelist.impl.WrappedMerchantRecipeListv1_8_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipelist.impl.WrappedMerchantRecipeListv1_8_R2;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipelist.impl.WrappedMerchantRecipeListv1_8_R3;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipelist.impl.WrappedMerchantRecipeListv1_9_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipelist.impl.WrappedMerchantRecipeListv1_9_R2;

public abstract class WrappedMerchantRecipeList<T> extends Wrappable<T> {
	
	static { // include in maven shade plugin
		WrappedMerchantRecipeListv1_8_R1.class.getClass();
		WrappedMerchantRecipeListv1_8_R2.class.getClass();
		WrappedMerchantRecipeListv1_8_R3.class.getClass();
		WrappedMerchantRecipeListv1_9_R1.class.getClass();
		WrappedMerchantRecipeListv1_9_R2.class.getClass();
		WrappedMerchantRecipeListv1_10_R1.class.getClass();
		WrappedMerchantRecipeListv1_11_R1.class.getClass();
		WrappedMerchantRecipeListv1_12_R1.class.getClass();
	}
	
	public abstract List<WrappedMerchantRecipe<?>> getRecipes();
	
	public abstract void setRecipes(List<WrappedMerchantRecipe<?>> recipes);
	
}
