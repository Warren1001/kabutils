package com.kabryxis.kabutils.spigot.version.custom.arrow.trailing.impl;

import com.kabryxis.kabutils.spigot.concurrent.BukkitTaskManager;
import com.kabryxis.kabutils.spigot.plugin.particleapi.ParticleInfo;
import com.kabryxis.kabutils.spigot.version.custom.arrow.trailing.TrailingArrow;
import net.minecraft.server.v1_13_R2.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftArrow;
import org.bukkit.craftbukkit.v1_13_R2.event.CraftEventFactory;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TrailingArrowv1_13_R2 extends EntityArrow implements TrailingArrow {

	protected final Queue<Location> locations = new ConcurrentLinkedQueue<>();

	protected final BukkitTaskManager taskManager;
	protected final int maxTickLife = 100;

	protected ParticleInfo particleInfo;
	protected BukkitTask task;
	protected int tickLife = 0;

	protected IBlockData az;
	protected int aB = 0;

	public TrailingArrowv1_13_R2(Location loc, Vector velocity, int speed, BukkitTaskManager taskManager, ParticleInfo particleInfo) {
		super(EntityTypes.ARROW, ((CraftWorld)loc.getWorld()).getHandle());
		fromPlayer = EntityArrow.PickupStatus.DISALLOWED;
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
				locations.forEach(TrailingArrowv1_13_R2.this::displayTrail);
				tick++;
			}

		}, 0, 1);
	}
	
	public void displayTrail(Location loc) {
		particleInfo.display(loc);
	}
	
	@Override
	public void tick() {
		super.tick();
		boolean flag = this.q();
		if (this.lastPitch == 0.0F && this.lastYaw == 0.0F) {
			float f = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
			this.yaw = (float)(MathHelper.c(this.motX, this.motZ) * 57.2957763671875D);
			this.pitch = (float)(MathHelper.c(this.motY, f) * 57.2957763671875D);
			this.lastYaw = this.yaw;
			this.lastPitch = this.pitch;
		}
		
		BlockPosition blockposition = new BlockPosition(this.tileX, this.tileY, this.tileZ);
		IBlockData iblockdata = this.world.getType(blockposition);
		if (!iblockdata.isAir() && !flag) {
			VoxelShape voxelshape = iblockdata.getCollisionShape(this.world, blockposition);
			if (!voxelshape.isEmpty()) {
				Iterator<AxisAlignedBB> iterator = voxelshape.d().iterator();
				while(iterator.hasNext()) {
					AxisAlignedBB axisalignedbb = iterator.next();
					if (axisalignedbb.a(blockposition).b(new Vec3D(this.locX, this.locY, this.locZ))) {
						this.inGround = true;
						break;
					}
				}
			}
		}
		
		if (this.shake > 0) {
			--this.shake;
		}
		
		if (this.ao()) {
			this.extinguish();
		}
		
		if (this.inGround && !flag) {
			if (this.az != iblockdata && this.world.getCubes(null, this.getBoundingBox().g(0.05D))) {
				this.inGround = false;
				this.motX *= this.random.nextFloat() * 0.2F;
				this.motY *= this.random.nextFloat() * 0.2F;
				this.motZ *= this.random.nextFloat() * 0.2F;
				this.aB = 0;
			} else {
				this.f();
			}
			
			++this.c;
		} else {
			this.c = 0;
			++this.aB;
			Vec3D vec3d = new Vec3D(this.locX, this.locY, this.locZ);
			Vec3D vec3d1 = new Vec3D(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
			MovingObjectPosition movingobjectposition = this.world.rayTrace(vec3d, vec3d1, FluidCollisionOption.NEVER, true, false);
			vec3d = new Vec3D(this.locX, this.locY, this.locZ);
			vec3d1 = new Vec3D(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
			if (movingobjectposition != null) {
				vec3d1 = new Vec3D(movingobjectposition.pos.x, movingobjectposition.pos.y, movingobjectposition.pos.z);
			}
			
			Entity entity = this.a(vec3d, vec3d1);
			if (entity != null) {
				movingobjectposition = new MovingObjectPosition(entity);
			}
			
			if (movingobjectposition != null && movingobjectposition.entity instanceof EntityHuman) {
				EntityHuman entityhuman = (EntityHuman)movingobjectposition.entity;
				Entity entity1 = this.getShooter();
				if (entity1 instanceof EntityHuman && !((EntityHuman)entity1).a(entityhuman)) {
					movingobjectposition = null;
				}
			}
			
			if (movingobjectposition != null && !flag) {
				this.a(movingobjectposition);
				this.impulse = true;
			}
			
			for(int j = 0; j < 16; j++) {
				locations.offer(new Location(world.getWorld(), locX + motX * (double)j / 8.0D, locY + motY * (double)j / 8.0D,
						locZ + motZ * (double)j / 8.0D));
			}
			
			this.locX += this.motX;
			this.locY += this.motY;
			this.locZ += this.motZ;
			float f1 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
			if (flag) {
				this.yaw = (float)(MathHelper.c(-this.motX, -this.motZ) * 57.2957763671875D);
			} else {
				this.yaw = (float)(MathHelper.c(this.motX, this.motZ) * 57.2957763671875D);
			}
			
			this.pitch = (float)(MathHelper.c(this.motY, f1) * 57.2957763671875D);
			while(this.pitch - this.lastPitch < -180.0F) {
				this.lastPitch -= 360.0F;
			}
			
			while(this.pitch - this.lastPitch >= 180.0F) {
				this.lastPitch += 360.0F;
			}
			
			while(this.yaw - this.lastYaw < -180.0F) {
				this.lastYaw -= 360.0F;
			}
			
			while(this.yaw - this.lastYaw >= 180.0F) {
				this.lastYaw += 360.0F;
			}
			
			this.pitch = this.lastPitch + (this.pitch - this.lastPitch) * 0.2F;
			this.yaw = this.lastYaw + (this.yaw - this.lastYaw) * 0.2F;
			float f2 = 0.99F;
			if (this.isInWater()) {
				for(int j = 0; j < 4; ++j) {
					this.world.addParticle(Particles.e, this.locX - this.motX * 0.25D, this.locY - this.motY * 0.25D, this.locZ - this.motZ * 0.25D, this.motX, this.motY, this.motZ);
				}
				
				f2 = this.p();
			}
			
			this.motX *= f2;
			this.motY *= f2;
			this.motZ *= f2;
			if (!this.isNoGravity() && !flag) {
				this.motY -= 0.05000000074505806D;
			}
			
			this.setPosition(this.locX, this.locY, this.locZ);
			this.checkBlockCollisions();
			
			tickLife++;
			if(tickLife >= maxTickLife) super.die();
		}
		
	}
	
	@Nullable
	protected Entity a(Vec3D vec3d, Vec3D vec3d1) {
		/*Entity entity = null;
		List<Entity> list = this.world.getEntities(this, this.getBoundingBox().b(this.motX, this.motY, this.motZ).g(1.0D), g);
		double d0 = 0.0D;
		
		for(int i = 0; i < list.size(); ++i) {
			Entity entity1 = list.get(i);
			if (entity1 != this.getShooter() || this.aB >= 5) {
				AxisAlignedBB axisalignedbb = entity1.getBoundingBox().g(0.30000001192092896D);
				MovingObjectPosition movingobjectposition = axisalignedbb.b(vec3d, vec3d1);
				if (movingobjectposition != null) {
					double d1 = vec3d.distanceSquared(movingobjectposition.pos);
					if (d1 < d0 || d0 == 0.0D) {
						entity = entity1;
						d0 = d1;
					}
				}
			}
		}
		
		return entity;*/
		return null;
	}
	
	@Override
	protected ItemStack getItemStack() {
		return ItemStack.a;
	}
	
	@Override
	protected void a(MovingObjectPosition movingobjectposition) {
		CraftEventFactory.callProjectileHitEvent(this, movingobjectposition);
		if (movingobjectposition.entity != null) {
			this.b(movingobjectposition);
		} else {
			BlockPosition blockposition = movingobjectposition.getBlockPosition();
			this.tileX = blockposition.getX();
			this.tileY = blockposition.getY();
			this.tileZ = blockposition.getZ();
			IBlockData iblockdata = this.world.getType(blockposition);
			this.az = iblockdata;
			this.motX = (float)(movingobjectposition.pos.x - this.locX);
			this.motY = (float)(movingobjectposition.pos.y - this.locY);
			this.motZ = (float)(movingobjectposition.pos.z - this.locZ);
			float f = MathHelper.sqrt(this.motX * this.motX + this.motY * this.motY + this.motZ * this.motZ) * 20.0F;
			this.locX -= this.motX / (double)f;
			this.locY -= this.motY / (double)f;
			this.locZ -= this.motZ / (double)f;
			this.a(this.i(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
			this.inGround = true;
			this.shake = 7;
			this.setCritical(false);
			if (!iblockdata.isAir()) {
				this.az.a(this.world, blockposition, this);
			}
		}
	}
	
	@Override
	public void inactiveTick() {}
	
	@Override
	public void d(net.minecraft.server.v1_13_R2.EntityHuman entityhuman) {}
	
	@Override
	public void b(net.minecraft.server.v1_13_R2.NBTTagCompound nbttagcompound) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void a(net.minecraft.server.v1_13_R2.NBTTagCompound nbttagcompound) {
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
	
	@Override
	public boolean isCollidable() {
		return false;
	}
	
}
