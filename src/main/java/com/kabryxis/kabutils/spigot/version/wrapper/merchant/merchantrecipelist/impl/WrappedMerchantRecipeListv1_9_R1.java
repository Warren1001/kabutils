package com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipelist.impl;

import java.util.ArrayList;
import java.util.List;

import com.kabryxis.kabutils.spigot.version.wrapper.WrapperCache;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe.WrappedMerchantRecipe;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe.impl.WrappedMerchantRecipev1_9_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipelist.WrappedMerchantRecipeList;

import net.minecraft.server.v1_9_R1.MerchantRecipe;
import net.minecraft.server.v1_9_R1.MerchantRecipeList;

public class WrappedMerchantRecipeListv1_9_R1 extends WrappedMerchantRecipeList<MerchantRecipeList> {
	
	@Override
	public List<WrappedMerchantRecipe<?>> getRecipes() {
		MerchantRecipeList rList = get();
		List<WrappedMerchantRecipe<?>> list = new ArrayList<>(rList.size());
		for(Object obj : rList) {
			MerchantRecipe recipe = (MerchantRecipe)obj;
			WrappedMerchantRecipev1_9_R1 handle = (WrappedMerchantRecipev1_9_R1)WrapperCache.get(WrappedMerchantRecipe.class);
			handle.set(recipe);
			list.add(handle);
		}
		return list;
	}
	
	@Override
	public void setRecipes(List<WrappedMerchantRecipe<?>> recipes) {
		MerchantRecipeList list = get();
		list.clear();
		recipes.forEach(r -> list.add(((WrappedMerchantRecipev1_9_R1)r).get()));
	}
	
}
