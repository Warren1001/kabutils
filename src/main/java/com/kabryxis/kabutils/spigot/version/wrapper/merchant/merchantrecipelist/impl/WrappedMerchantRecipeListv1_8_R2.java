package com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipelist.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe.WrappedMerchantRecipe;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe.impl.WrappedMerchantRecipev1_8_R2;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipelist.WrappedMerchantRecipeList;
import net.minecraft.server.v1_8_R2.MerchantRecipe;
import net.minecraft.server.v1_8_R2.MerchantRecipeList;

import java.util.ArrayList;
import java.util.List;

public class WrappedMerchantRecipeListv1_8_R2 extends WrappedMerchantRecipeList {
	
	private final MerchantRecipeList merchantRecipeList;
	
	public WrappedMerchantRecipeListv1_8_R2() {
		this.merchantRecipeList = new MerchantRecipeList();
	}
	
	public WrappedMerchantRecipeListv1_8_R2(MerchantRecipeList merchantRecipeList) {
		this.merchantRecipeList = merchantRecipeList;
	}
	
	@Override
	public Object getObject() {
		return merchantRecipeList;
	}
	
	@Override
	public List<WrappedMerchantRecipe> getRecipes() {
		List<WrappedMerchantRecipe> list = new ArrayList<>(merchantRecipeList.size());
		for(MerchantRecipe merchantRecipe : merchantRecipeList) {
			list.add(new WrappedMerchantRecipev1_8_R2(merchantRecipe));
		}
		return list;
	}
	
	@Override
	public void setRecipes(List<WrappedMerchantRecipe> recipes) {
		merchantRecipeList.clear();
		recipes.forEach(r -> merchantRecipeList.add(((WrappedMerchantRecipev1_8_R2)r).getHandle()));
	}
	
}
