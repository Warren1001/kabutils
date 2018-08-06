package com.kabryxis.kabutils.spigot.version.custom.dragon.pet;

import com.kabryxis.kabutils.spigot.version.Version;
import com.kabryxis.kabutils.spigot.version.custom.dragon.pet.impl.PetDragonv1_8_R1;
import com.kabryxis.kabutils.spigot.version.custom.dragon.pet.impl.PetDragonv1_8_R3;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.function.Function;
import java.util.function.Predicate;

public class PetDragons {
	
	private static final Function<Object[], PetDragon> supplier;
	private static final Predicate<Entity> isPetDragon;
	
	static {
		switch(Version.VERSION) {
			case v1_8_R1:
				PetDragonv1_8_R1.register();
				supplier = PetDragonv1_8_R1::new;
				isPetDragon = PetDragonv1_8_R1::isPetDragon;
				break;
			case v1_8_R3:
				PetDragonv1_8_R3.register();
				supplier = PetDragonv1_8_R3::new;
				isPetDragon = PetDragonv1_8_R3::isPetDragon;
				break;
			default:
				supplier = null;
				isPetDragon = null;
				break;
		}
	}
	
	public static PetDragon newInstance(Location spawn, Player owner, Location center) {
		return supplier.apply(new Object[] { spawn, owner, center });
	}
	
	public static boolean isPetDragon(Entity entity) {
		return isPetDragon.test(entity);
	}
	
}
