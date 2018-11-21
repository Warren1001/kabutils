package com.kabryxis.kabutils.spigot.version.custom.dragon.pet;

import com.kabryxis.kabutils.spigot.version.custom.CustomEntity;
import com.kabryxis.kabutils.spigot.version.custom.CustomEntityRegistry;
import com.kabryxis.kabutils.spigot.version.custom.dragon.pet.impl.PetDragonv1_8_R1;
import com.kabryxis.kabutils.spigot.version.custom.dragon.pet.impl.PetDragonv1_8_R3;
import com.kabryxis.kabutils.spigot.version.wrapper.WrapperFactory;
import org.bukkit.Location;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface PetDragon extends CustomEntity {
	
	Class<PetDragonv1_8_R1> v1_8_R1 = PetDragonv1_8_R1.class;
	Class<PetDragonv1_8_R3> v1_8_R3 = PetDragonv1_8_R3.class;
	Class<? extends PetDragon> IMPLEMENTATION_CLASS = WrapperFactory.getImplementationClass(PetDragon.class);
	
	static void register() {
		CustomEntityRegistry.registerEntity("EnderDragon", IMPLEMENTATION_CLASS);
	}
	
	static PetDragon spawn(Location spawn, Player owner, Location center) {
		return WrapperFactory.getSupplier(PetDragon.class, Location.class, Player.class, Location.class).apply(spawn, owner, center);
	}
	
	static boolean is(Entity entity) {
		return WrapperFactory.isHandleInstance(entity, PetDragon.class);
	}
	
	static PetDragon cast(Entity entity) {
		return WrapperFactory.castHandle(entity, PetDragon.class);
	}
	
	void setTarget(Player player);
	
	@Override
	EnderDragon getBukkitEntity();
	
}
