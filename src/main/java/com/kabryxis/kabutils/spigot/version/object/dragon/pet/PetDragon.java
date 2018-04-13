package com.kabryxis.kabutils.spigot.version.object.dragon.pet;

import org.bukkit.entity.Player;

public interface PetDragon {
	
	/*private static final TriFunction<Location, Player, Vector, PetDragon> supplier;
	
	static { // include in maven shade plugin
		if(Version.VERSION == Version.v1_8_R1) {
			supplier = PetDragonv1_8_R1::new;
		}
		else supplier = null;
	}
	
	public static PetDragon newInstance(Location spawn, Player owner, Vector center) {
		return supplier.apply(spawn, owner, center);
	}*/
	
	void setTarget(Player player);
	
}
