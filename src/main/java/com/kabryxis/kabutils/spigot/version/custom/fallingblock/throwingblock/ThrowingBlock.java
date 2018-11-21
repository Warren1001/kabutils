package com.kabryxis.kabutils.spigot.version.custom.fallingblock.throwingblock;

import com.kabryxis.kabutils.AutoRemovable;
import com.kabryxis.kabutils.spigot.version.custom.CustomEntity;
import com.kabryxis.kabutils.spigot.version.custom.CustomEntityRegistry;
import com.kabryxis.kabutils.spigot.version.custom.fallingblock.throwingblock.impl.ThrowingBlockv1_8_R3;
import com.kabryxis.kabutils.spigot.version.wrapper.WrapperFactory;
import com.kabryxis.kabutils.spigot.world.BlockStateManager;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.util.Vector;

import java.util.function.Predicate;

public interface ThrowingBlock extends CustomEntity, AutoRemovable {
	
	Class<ThrowingBlockv1_8_R3> v1_8_R3 = ThrowingBlockv1_8_R3.class;
	Class<? extends ThrowingBlock> IMPLEMENTATION_CLASS = WrapperFactory.getImplementationClass(ThrowingBlock.class);
	
	static void register() {
		CustomEntityRegistry.registerEntity("FallingSand", IMPLEMENTATION_CLASS);
	}
	
	static ThrowingBlock spawn(BlockStateManager blockStateManager, Block block, Vector velocity, long aliveTime, Predicate<Block> protectedPredicate) {
		return WrapperFactory.getSupplier(ThrowingBlock.class, BlockStateManager.class, Block.class, Vector.class, long.class, Predicate.class).apply
				(blockStateManager, block, velocity, aliveTime, protectedPredicate);
	}
	
	static boolean is(Entity entity) {
		return WrapperFactory.isHandleInstance(entity, ThrowingBlock.class);
	}
	
	static ThrowingBlock cast(Entity entity) {
		return WrapperFactory.castHandle(entity, ThrowingBlock.class);
	}
	
	@Override
	FallingBlock getBukkitEntity();
	
}
