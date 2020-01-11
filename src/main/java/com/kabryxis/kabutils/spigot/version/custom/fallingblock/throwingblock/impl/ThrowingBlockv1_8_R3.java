package com.kabryxis.kabutils.spigot.version.custom.fallingblock.throwingblock.impl;

import com.kabryxis.kabutils.spigot.version.custom.fallingblock.throwingblock.ThrowingBlock;
import com.kabryxis.kabutils.spigot.world.BlockStateManager;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftFallingSand;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.util.Vector;

import java.util.function.Predicate;

public class ThrowingBlockv1_8_R3 extends EntityFallingBlock implements ThrowingBlock {

	private final BlockStateManager blockStateManager;
	private final BlockState originState;
	private final long aliveTime;
	private final BlockData effectData;
	private final long spawnTimestamp;
	private final Predicate<org.bukkit.block.Block> protectedPredicate;

	private BlockState landedState;
	
	public ThrowingBlockv1_8_R3(BlockStateManager blockStateManager, org.bukkit.block.Block block, Vector velocity, long aliveTime,
								Predicate<org.bukkit.block.Block> protectedPredicate) {
		super(((CraftWorld)block.getWorld()).getHandle(), block.getX(), block.getY(), block.getZ(),
				Block.getById(block.getType().getId()).fromLegacyData(block.getData()));
		this.blockStateManager = blockStateManager;
		this.protectedPredicate = protectedPredicate;
		this.aliveTime = aliveTime;
		originState = blockStateManager.createState(block);
		effectData = block.getType().createBlockData();
		blockStateManager.setBlock(block, org.bukkit.Material.AIR.createBlockData());
		playEffect(block.getX(), block.getY(), block.getZ(), effectData);
		setLocation(block.getX() + 0.5, block.getY() + 0.5, block.getZ() + 0.5, 0F, 0F);
		world.addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);
		getBukkitEntity().setVelocity(velocity);
		spawnTimestamp = System.currentTimeMillis();
	}
	
	@Override
	public void forceRemove() {
		die();
	}
	
	@Override
	public CraftFallingSand getBukkitEntity() {
		return (CraftFallingSand)super.getBukkitEntity();
	}
	
	public void playEffect(int x, int y, int z, BlockData effectData) {
		/*ParticleEffect.BLOCK_DUST.sendData(world.getWorld().getPlayers(), x + 0.5, y + 0.5, z + 0.5,
				0.5, 0.5, 0.5, 0, 70, effectData);*/
		world.getWorld().getPlayers().forEach(player -> player.spawnParticle(Particle.BLOCK_DUST, x + 0.5, y + 0.5, z + 0.5, 70, 0.5, 0.5, 0.5, 0, effectData));
		world.getWorld().playSound(new Location(world.getWorld(), x, y, z), Sound.BLOCK_STONE_STEP, 2F, 1F);
	}
	
	@Override
	public void remove(boolean silent) {
		if(silent) {
			if(isAlive()) die();
			return;
		}
		if(landedState != null) {
			landedState.update(true, false);
			playEffect(landedState.getX(), landedState.getY(), landedState.getZ(), landedState.getType().createBlockData());
		}
		else if(isAlive()) die();
		originState.update(true, false);
		playEffect(originState.getX(), originState.getY(), originState.getZ(), effectData);
	}
	
	@Override
	public void t_() {
		IBlockData blockData = getBlock();
		Block block = blockData.getBlock();
		lastX = locX;
		lastY = locY;
		lastZ = locZ;
		motY -= 0.03999999910593033;
		move(motX, motY, motZ);
		motX *= 0.9800000190734863;
		motY *= 0.9800000190734863;
		motZ *= 0.9800000190734863;
		if(onGround) {
			motX *= 0.699999988079071;
			motZ *= 0.699999988079071;
			motY = -1;
			if(System.currentTimeMillis() - spawnTimestamp > aliveTime) {
				BlockPosition pos = new BlockPosition(this);
				die();
				if(world.getType(pos).getBlock() != Blocks.PISTON_EXTENSION && world.a(block, pos, true, EnumDirection.UP, null, null)
						&& pos.getX() >= -30000000 && pos.getZ() >= -30000000 && pos.getX() < 30000000 && pos.getZ() < 30000000 && pos.getY() >= 0
						&& pos.getY() < world.getWorld().getMaxHeight()) {
					if(world.getType(pos) == blockData) {
						playEffect(pos.getX(), pos.getY(), pos.getZ(), effectData);
						return;
					}
					if(BlockFalling.canFall(world, pos.down())) {
						pos = new BlockPosition(lastX, lastY, lastZ);
						if(!BlockFalling.canFall(world, pos)) return;
					}
					if(originState.getX() != pos.getX() || originState.getZ() != pos.getZ() || Math.abs(originState.getY() - pos.getY()) > 2) {
						BlockState landedState = blockStateManager.createState(pos.getX(), pos.getY(), pos.getZ());
						org.bukkit.block.Block landedBlock = landedState.getBlock();
						if(!protectedPredicate.test(landedBlock)) {
							this.landedState = landedState;
							blockStateManager.setBlock(landedBlock, originState.getBlockData());
							playEffect(pos.getX(), pos.getY(), pos.getZ(), effectData);
						}
					}
				}
			}
		}
		else {
			world.getEntities(this, getBoundingBox()).forEach(e -> {
				CraftEventFactory.entityDamage = this;
				e.damageEntity(DamageSource.FALLING_BLOCK, 0.5F);
			});
			CraftEventFactory.entityDamage = null;
		}
	}

}
