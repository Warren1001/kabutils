package com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipelist.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe.WrappedMerchantRecipe;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe.impl.WrappedMerchantRecipev1_8_R3;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipelist.WrappedMerchantRecipeList;
import net.minecraft.server.v1_8_R3.MerchantRecipe;
import net.minecraft.server.v1_8_R3.MerchantRecipeList;

import java.util.ArrayList;
import java.util.List;

public class WrappedMerchantRecipeListv1_8_R3 implements WrappedMerchantRecipeList {
	
	private MerchantRecipeList merchantRecipeList;
	
	public WrappedMerchantRecipeListv1_8_R3(boolean newEmpty) {
		setHandle(newEmpty);
	}
	
	public WrappedMerchantRecipeListv1_8_R3(MerchantRecipeList merchantRecipeList) {
		this.merchantRecipeList = merchantRecipeList;
	}
	
	@Override
	public WrappedMerchantRecipeListv1_8_R3 setHandle(Object obj) {
		if(obj instanceof MerchantRecipeList) merchantRecipeList = (MerchantRecipeList)obj;
		else if(obj instanceof Boolean) if((Boolean)obj) merchantRecipeList = new MerchantRecipeList();
		return this;
	}
	
	@Override
	public MerchantRecipeList getHandle() {
		return merchantRecipeList;
	}
	
	@Override
	public void clear() {
		merchantRecipeList = null;
	}
	
	@Override
	public List<WrappedMerchantRecipe> getRecipes() {
		List<WrappedMerchantRecipe> list = new ArrayList<>(merchantRecipeList.size());
		for(MerchantRecipe merchantRecipe : merchantRecipeList) {
			list.add(new WrappedMerchantRecipev1_8_R3(merchantRecipe));
		}
		return list;
	}
	
	@Override
	public void setRecipes(List<WrappedMerchantRecipe> recipes) {
		merchantRecipeList.clear();
		recipes.forEach(r -> merchantRecipeList.add(((WrappedMerchantRecipev1_8_R3)r).getHandle()));
	}
	
}
