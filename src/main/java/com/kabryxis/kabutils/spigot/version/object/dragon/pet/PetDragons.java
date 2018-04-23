package com.kabryxis.kabutils.spigot.version.object.dragon.pet;

import com.kabryxis.kabutils.spigot.utility.TriFunction;
import com.kabryxis.kabutils.spigot.version.Version;
import com.kabryxis.kabutils.spigot.version.object.dragon.pet.impl.PetDragonv1_8_R1;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class PetDragons {
	
	private static final TriFunction<Location, Player, Vector, PetDragon> supplier;
	
	static { // include in maven shade plugin
		if(Version.VERSION == Version.v1_8_R1) {
			supplier = PetDragonv1_8_R1::new;
			PetDragonv1_8_R1.register();
		}
		else supplier = null;
	}
	
	public static PetDragon newInstance(Location spawn, Player owner, Vector center) {
		return supplier.apply(spawn, owner, center);
	}
	
}
