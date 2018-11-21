package com.kabryxis.kabutils.spigot.version.custom.arrow.trailing.impl;

import com.kabryxis.kabutils.spigot.concurrent.BukkitTaskManager;
import com.kabryxis.kabutils.spigot.plugin.particleapi.ParticleInfo;
import com.kabryxis.kabutils.spigot.version.custom.arrow.trailing.TrailingArrow;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArrow;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TrailingArrowv1_8_R3 extends EntityArrow implements TrailingArrow {

	protected final Queue<Location> locations = new ConcurrentLinkedQueue<>();

	protected final BukkitTaskManager taskManager;
	protected final int maxTickLife = 100;

	protected ParticleInfo particleInfo;
	protected BukkitTask task;

	protected int d;
	protected int e;
	protected int f;
	protected Block g;
	protected int h;
	protected int tickLife = 0;

	public TrailingArrowv1_8_R3(Location loc, Vector velocity, int speed, BukkitTaskManager taskManager, ParticleInfo particleInfo) {
		super(((CraftWorld)loc.getWorld()).getHandle());
		this.taskManager = taskManager;
		this.particleInfo = particleInfo;
		setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		shoot(velocity.getX(), velocity.getY(), velocity.getZ(), speed, 0);
		world.addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);
		task = taskManager.start(new BukkitRunnable() {

			private int tick;

			@Override
			public void run() {
				if(tick > 30) {
					Location loc = null;
					for(int i = 0; i < 8; i++) {
						loc = locations.poll();
						if(loc == null) {
							cancel();
							return;
						}
					}
					loc.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), 2.0F, false, false);
				}
				locations.forEach(TrailingArrowv1_8_R3.this::displayTrail);
				tick++;
			}

		}, 0, 1);
	}

	@Override
	public void t_() {
		K();
		if(lastPitch == 0.0F && lastYaw == 0.0F) {
			lastYaw = yaw = (float)(MathHelper.b(motX, motZ) * 180.0D / 3.1415927410125732D);
			lastPitch = pitch = (float)(MathHelper.b(motY, MathHelper.sqrt(motX * motX + motZ * motZ)) * 180.0D / 3.1415927410125732D);
		}
		BlockPosition blockposition = new BlockPosition(d, e, f);
		IBlockData iblockdata = world.getType(blockposition);
		Block block = iblockdata.getBlock();
		if(block.getMaterial() != Material.AIR) {
			block.updateShape(world, blockposition);
			AxisAlignedBB axisalignedbb = block.a(world, blockposition, iblockdata);
			if(axisalignedbb != null && axisalignedbb.a(new Vec3D(locX, locY, locZ))) inGround = true;
		}
		if(shake > 0) shake--;
		if(inGround) die();
		else {
			Vec3D vec3d = new Vec3D(locX, locY, locZ);
			Vec3D vec3d1 = new Vec3D(locX + motX, locY + motY, locZ + motZ);
			MovingObjectPosition movingobjectposition = world.rayTrace(vec3d, vec3d1, false, true, false);
			if(movingobjectposition != null && movingobjectposition.entity != null) movingobjectposition = null;
			if(movingobjectposition != null) {
				CraftEventFactory.callProjectileHitEvent(this);
				BlockPosition blockposition1 = movingobjectposition.a();
				d = blockposition1.getX();
				e = blockposition1.getY();
				f = blockposition1.getZ();
				IBlockData iblockdata1 = world.getType(blockposition1);
				g = iblockdata1.getBlock();
				h = g.toLegacyData(iblockdata1);
				motX = (double)((float)(movingobjectposition.pos.a - locX));
				motY = (double)((float)(movingobjectposition.pos.b - locY));
				motZ = (double)((float)(movingobjectposition.pos.c - locZ));
				float f1 = MathHelper.sqrt(motX * motX + motY * motY + motZ * motZ);
				locX -= motX / (double)f1 * 0.05000000074505806D;
				locY -= motY / (double)f1 * 0.05000000074505806D;
				locZ -= motZ / (double)f1 * 0.05000000074505806D;
				makeSound("random.bowhit", 1.0F, 1.2F / (random.nextFloat() * 0.2F + 0.9F));
				inGround = true;
				shake = 7;
				setCritical(false);
				if(g.getMaterial() != Material.AIR) g.a(world, blockposition1, iblockdata1, this);
			}
			for(int j = 0; j < 16; j++) {
				locations.offer(new Location(world.getWorld(), locX + motX * (double)j / 8.0D, locY + motY * (double)j / 8.0D,
						locZ + motZ * (double)j / 8.0D));
			}
			locX += motX;
			locY += motY;
			locZ += motZ;
			yaw = (float)(MathHelper.b(motX, motZ) * 180.0D / 3.1415927410125732D);
			pitch = (float)(MathHelper.b(motY, MathHelper.sqrt(motX * motX + motZ * motZ)) * 180.0D / 3.1415927410125732D);
			while(pitch - lastPitch < -180.0F) {
				lastPitch -= 360.0F;
			}
			while(pitch - lastPitch >= 180.0F) {
				lastPitch += 360.0F;
			}
			while(yaw - lastYaw < -180.0F) {
				lastYaw -= 360.0F;
			}
			while(yaw - lastYaw >= 180.0F) {
				lastYaw += 360.0F;
			}
			pitch = lastPitch + (pitch - lastPitch) * 0.2F;
			yaw = lastYaw + (yaw - lastYaw) * 0.2F;
			motY -= 0.01;
			setPosition(locX, locY, locZ);
			checkBlockCollisions();
			tickLife++;
			if(tickLife >= maxTickLife) die();
		}
	}

	public void displayTrail(Location loc) {
		particleInfo.display(loc);
	}

	@Override
	public void inactiveTick() {}

	@Override
	public void d(EntityHuman entityhuman) {}

	@Override
	public void b(NBTTagCompound nbttagcompound) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void a(NBTTagCompound nbttagcompound) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCritical(boolean flag) {}

	@Override
	public boolean isCritical() {
		return false;
	}

	@Override
	public void forceRemove() {
		super.die();
	}

	@Override
	public CraftArrow getBukkitEntity() {
		return (CraftArrow)super.getBukkitEntity();
	}

}
