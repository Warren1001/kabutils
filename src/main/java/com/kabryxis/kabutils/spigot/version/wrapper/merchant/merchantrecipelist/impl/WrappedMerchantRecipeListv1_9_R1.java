package com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipelist.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe.WrappedMerchantRecipe;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe.impl.WrappedMerchantRecipev1_9_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipelist.WrappedMerchantRecipeList;
import net.minecraft.server.v1_9_R1.MerchantRecipe;
import net.minecraft.server.v1_9_R1.MerchantRecipeList;

import java.util.ArrayList;
import java.util.List;

public class WrappedMerchantRecipeListv1_9_R1 implements WrappedMerchantRecipeList {
	
	private MerchantRecipeList merchantRecipeList;
	
	public WrappedMerchantRecipeListv1_9_R1(boolean newEmpty) {
		setHandle(newEmpty);
	}
	
	public WrappedMerchantRecipeListv1_9_R1(MerchantRecipeList merchantRecipeList) {
		this.merchantRecipeList = merchantRecipeList;
	}
	
	@Override
	public void setHandle(Object obj) {
		if(obj instanceof MerchantRecipeList) merchantRecipeList = (MerchantRecipeList)obj;
		else if(obj instanceof Boolean) if((Boolean)obj) merchantRecipeList = new MerchantRecipeList();
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
			list.add(new WrappedMerchantRecipev1_9_R1(merchantRecipe));
		}
		return list;
	}
	
	@Override
	public void setRecipes(List<WrappedMerchantRecipe> recipes) {
		merchantRecipeList.clear();
		recipes.forEach(r -> merchantRecipeList.add(((WrappedMerchantRecipev1_9_R1)r).getHandle()));
	}
	
}
