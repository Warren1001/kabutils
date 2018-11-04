package com.kabryxis.kabutils.spigot.version.custom.fallingblock.throwingblock;

import com.kabryxis.kabutils.spigot.version.custom.CustomEntity;
import com.kabryxis.kabutils.spigot.version.custom.CustomEntityRegistry;
import com.kabryxis.kabutils.spigot.version.custom.fallingblock.throwingblock.impl.ThrowingBlockv1_8_R3;
import com.kabryxis.kabutils.spigot.version.wrapper.WrapperFactory;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;

public interface ThrowingBlock extends CustomEntity {
	
	Class<? extends ThrowingBlock> v1_8_R3 = ThrowingBlockv1_8_R3.class;
	Class<? extends ThrowingBlock> IMPLEMENTATION_CLASS = WrapperFactory.getImplementationClass(ThrowingBlock.class);
	
	static void register() {
		CustomEntityRegistry.registerEntity("FallingSand", IMPLEMENTATION_CLASS);
	}
	
	static ThrowingBlock spawn(Location loc, Material type, byte data, long aliveTime) {
		return WrapperFactory.get(ThrowingBlock.class, new Class[] { Location.class, Material.class, byte.class, long.class }, new Object[] { loc, type, data, aliveTime });
	}
	
	static boolean is(Entity entity) {
		return WrapperFactory.isInstance(entity, ThrowingBlock.class);
	}
	
	static ThrowingBlock cast(Entity entity) {
		return WrapperFactory.castHandle(entity, ThrowingBlock.class);
	}
	
	long getUniqueId();
	
	boolean canBePlaced();
	
	@Override
	FallingBlock getBukkitEntity();
	
}
