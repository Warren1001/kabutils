package com.kabryxis.kabutils.spigot.version.custom.slime.hitbox;

import com.kabryxis.kabutils.spigot.version.custom.CustomEntity;
import com.kabryxis.kabutils.spigot.version.custom.CustomEntityRegistry;
import com.kabryxis.kabutils.spigot.version.custom.slime.hitbox.impl.CustomHitboxv1_8_R3;
import com.kabryxis.kabutils.spigot.version.wrapper.WrapperFactory;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Slime;

public interface CustomHitbox extends CustomEntity {
	
	Class<CustomHitboxv1_8_R3> v1_8_R3 = CustomHitboxv1_8_R3.class;
	Class<? extends CustomHitbox> IMPLEMENTATION = WrapperFactory.getImplementationClass(CustomHitbox.class);
	
	static void register() {
		CustomEntityRegistry.registerEntity("Slime", IMPLEMENTATION);
	}
	
	static CustomHitbox spawn(Location loc, String ownerName) {
		return WrapperFactory.getSupplier(CustomHitbox.class, Location.class, String.class).apply(loc, ownerName);
	}
	
	static boolean is(Entity entity) {
		return WrapperFactory.isInstance(entity, CustomHitbox.class);
	}
	
	static CustomHitbox cast(Entity entity) {
		return WrapperFactory.castHandle(entity, CustomHitbox.class);
	}
	
	@Override
	Slime getBukkitEntity();
	
}
