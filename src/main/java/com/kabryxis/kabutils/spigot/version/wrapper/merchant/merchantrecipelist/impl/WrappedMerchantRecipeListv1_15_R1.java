package com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipelist.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe.WrappedMerchantRecipe;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipe.impl.WrappedMerchantRecipev1_15_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipelist.WrappedMerchantRecipeList;
import net.minecraft.server.v1_15_R1.MerchantRecipeList;

import java.util.List;
import java.util.stream.Collectors;

public class WrappedMerchantRecipeListv1_15_R1 implements WrappedMerchantRecipeList {
	
	private MerchantRecipeList merchantRecipeList;
	
	public WrappedMerchantRecipeListv1_15_R1(boolean newEmpty) {
		setHandle(newEmpty);
	}
	
	public WrappedMerchantRecipeListv1_15_R1(MerchantRecipeList merchantRecipeList) {
		this.merchantRecipeList = merchantRecipeList;
	}
	
	@Override
	public WrappedMerchantRecipeListv1_15_R1 setHandle(Object obj) {
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
		return merchantRecipeList.stream().map(WrappedMerchantRecipev1_15_R1::new).collect(Collectors.toList());
	}
	
	@Override
	public void setRecipes(List<WrappedMerchantRecipe> recipes) {
		merchantRecipeList.clear();
		recipes.forEach(r -> merchantRecipeList.add(((WrappedMerchantRecipev1_15_R1)r).getHandle()));
	}
	
}
