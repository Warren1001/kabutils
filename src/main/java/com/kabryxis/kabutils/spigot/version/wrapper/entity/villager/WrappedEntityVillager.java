package com.kabryxis.kabutils.spigot.version.wrapper.entity.villager;

import com.kabryxis.kabutils.spigot.version.Version;
import com.kabryxis.kabutils.spigot.version.wrapper.Wrappable;
import com.kabryxis.kabutils.spigot.version.wrapper.entity.villager.impl.*;
import com.kabryxis.kabutils.spigot.version.wrapper.merchant.merchantrecipelist.WrappedMerchantRecipeList;
import org.bukkit.entity.Villager;

import java.util.function.Function;

public abstract class WrappedEntityVillager implements Wrappable {
	
	private static final Function<Villager, WrappedEntityVillager> supplier;
	
	static {
		switch(Version.VERSION) {
			case v1_8_R1:
				supplier = WrappedEntityVillagerv1_8_R1::new;
				break;
			case v1_8_R2:
				supplier = WrappedEntityVillagerv1_8_R2::new;
				break;
			case v1_8_R3:
				supplier = WrappedEntityVillagerv1_8_R3::new;
				break;
			case v1_9_R1:
				supplier = WrappedEntityVillagerv1_9_R1::new;
				break;
			case v1_9_R2:
				supplier = WrappedEntityVillagerv1_9_R2::new;
				break;
			case v1_10_R1:
				supplier = WrappedEntityVillagerv1_10_R1::new;
				break;
			case v1_11_R1:
				supplier = WrappedEntityVillagerv1_11_R1::new;
				break;
			case v1_12_R1:
				supplier = WrappedEntityVillagerv1_12_R1::new;
				break;
			default:
				supplier = null;
				break;
		}
	}
	
	public static WrappedEntityVillager newInstance(Villager villager) {
		return supplier.apply(villager);
	}
	
	public abstract WrappedMerchantRecipeList getOffers();
	
}
