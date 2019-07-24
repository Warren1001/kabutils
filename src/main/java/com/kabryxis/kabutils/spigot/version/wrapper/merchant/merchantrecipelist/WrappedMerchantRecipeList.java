package com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipelist;

import com.kabryxis.kabutils.spigot.version.wrapper.Wrappable;
import com.kabryxis.kabutils.spigot.version.wrapper.WrapperFactory;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe.WrappedMerchantRecipe;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipelist.impl.*;

import java.util.List;

public interface WrappedMerchantRecipeList extends Wrappable {
	
	Class<WrappedMerchantRecipeListv1_8_R1> v1_8_R1 = WrappedMerchantRecipeListv1_8_R1.class;
	Class<WrappedMerchantRecipeListv1_8_R2> v1_8_R2 = WrappedMerchantRecipeListv1_8_R2.class;
	Class<WrappedMerchantRecipeListv1_8_R3> v1_8_R3 = WrappedMerchantRecipeListv1_8_R3.class;
	Class<WrappedMerchantRecipeListv1_9_R1> v1_9_R1 = WrappedMerchantRecipeListv1_9_R1.class;
	Class<WrappedMerchantRecipeListv1_9_R2> v1_9_R2 = WrappedMerchantRecipeListv1_9_R2.class;
	Class<WrappedMerchantRecipeListv1_10_R1> v1_10_R1 = WrappedMerchantRecipeListv1_10_R1.class;
	Class<WrappedMerchantRecipeListv1_11_R1> v1_11_R1 = WrappedMerchantRecipeListv1_11_R1.class;
	Class<WrappedMerchantRecipeListv1_12_R1> v1_12_R1 = WrappedMerchantRecipeListv1_12_R1.class;
	Class<WrappedMerchantRecipeListv1_13_R2> v1_13_R2 = WrappedMerchantRecipeListv1_13_R2.class;
	Class<WrappedMerchantRecipeListv1_14_R1> v1_14_R1 = WrappedMerchantRecipeListv1_14_R1.class;
	
	static WrappedMerchantRecipeList newInstance() {
		return newInstance(true);
	}
	
	static WrappedMerchantRecipeList newInstance(boolean newEmpty) {
		return WrapperFactory.getSupplier(WrappedMerchantRecipeList.class, boolean.class).apply(newEmpty);
	}
	
	@Override
	WrappedMerchantRecipeList setHandle(Object obj);
	
	List<WrappedMerchantRecipe> getRecipes();
	
	void setRecipes(List<WrappedMerchantRecipe> recipes);
	
}
