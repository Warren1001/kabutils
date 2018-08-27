package com.kabryxis.kabutils.spigot.version.custom.dragon.pet.impl;

import com.kabryxis.kabutils.spigot.version.custom.dragon.pet.PetDragon;
import net.minecraft.server.v1_8_R1.*;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.Map;

public class PetDragonv1_8_R1 extends EntityInsentient implements IComplex, IMonster, PetDragon {
	
	public static boolean isPetDragon(org.bukkit.entity.Entity entity) {
		return ((CraftEntity)entity).getHandle() instanceof PetDragon;
	}
	
	public static void register() {
		String name = "EnderDragon";
		Class<?> clazz = PetDragonv1_8_R1.class;
		((Map<String, Class<?>>)getPrivateField("c", EntityTypes.class, null)).put(name, clazz);
		((Map<Class<?>, String>)getPrivateField("d", EntityTypes.class, null)).put(clazz, name);
		((Map<Class<?>, Integer>)getPrivateField("f", EntityTypes.class, null)).put(clazz, Integer.valueOf(63));
	}
	
	private static Object getPrivateField(String fieldName, Class<?> clazz, Object object) {
		Object o = null;
		try {
			Field field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			o = field.get(object);
		}
		catch(NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return o;
	}
	
	private final Player owner;
	private final double centerX, centerY, centerZ;
	
	private Entity currentTarget;
	private Player target;
	
	@Override
	public void setTarget(Player target) {
		this.target = target;
	}
	
	@Override
	public CraftEntity getBukkitEntity() {
		return super.getBukkitEntity();
	}
	
	public double a;
	public double b;
	public double c;
	public double[][] bi;
	public int bj;
	public EntityComplexPart[] children;
	public EntityComplexPart bl;
	public EntityComplexPart bm;
	public EntityComplexPart bn;
	public EntityComplexPart bo;
	public EntityComplexPart bp;
	public EntityComplexPart bq;
	public EntityComplexPart br;
	public float bs;
	public float bt;
	public boolean bu;
	public boolean bv;
	public int bw;
	
	public PetDragonv1_8_R1(Object[] objs) {
		this((Location)objs[0], (Player)objs[1], (Location)objs[2]);
	}
	
	public PetDragonv1_8_R1(Location loc, Player owner, Location center) {
		super(((CraftWorld)loc.getWorld()).getHandle());
		this.owner = owner;
		this.centerX = center.getX();
		this.centerY = center.getY();
		this.centerZ = center.getZ();
		this.bi = new double[64][3];
		this.bj = -1;
		this.children = new EntityComplexPart[] { this.bl = new EntityComplexPart(this, "head", 6.0f, 6.0f),
				this.bm = new EntityComplexPart(this, "body", 8.0f, 8.0f), this.bn = new EntityComplexPart(this, "tail", 4.0f, 4.0f),
				this.bo = new EntityComplexPart(this, "tail", 4.0f, 4.0f), this.bp = new EntityComplexPart(this, "tail", 4.0f, 4.0f),
				this.bq = new EntityComplexPart(this, "wing", 4.0f, 4.0f), this.br = new EntityComplexPart(this, "wing", 4.0f, 4.0f) };
		this.setHealth(this.getMaxHealth());
		this.a(16.0f, 8.0f);
		this.T = true;
		this.fireProof = true;
		this.b = 100.0;
		this.ah = true;
		setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		this.world.addEntity(this);
	}
	
	public double[] b(final int i, float f) {
		f = 1.0f - f;
		final int j = this.bj - i * 1 & 0x3F;
		final int k = this.bj - i * 1 - 1 & 0x3F;
		final double[] adouble = new double[3];
		double d0 = this.bi[j][0];
		double d2 = MathHelper.g(this.bi[k][0] - d0);
		adouble[0] = d0 + d2 * f;
		d0 = this.bi[j][1];
		d2 = this.bi[k][1] - d0;
		adouble[1] = d0 + d2 * f;
		adouble[2] = this.bi[j][2] + (this.bi[k][2] - this.bi[j][2]) * f;
		return adouble;
	}
	
	@Override
	public void m() {
		this.bs = this.bt;
		float f = 0.2f / (MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ) * 10.0f + 1.0f);
		f *= (float)Math.pow(2.0, this.motY);
		if(this.bv) {
			this.bt += f * 0.5f;
		}
		else {
			this.bt += f;
		}
		this.yaw = MathHelper.g(this.yaw);
		if(this.bj < 0) {
			for(int i = 0; i < this.bi.length; ++i) {
				this.bi[i][0] = this.yaw;
				this.bi[i][1] = this.locY;
			}
		}
		if(++this.bj == this.bi.length) {
			this.bj = 0;
		}
		this.bi[this.bj][0] = this.yaw;
		this.bi[this.bj][1] = this.locY;
		final double d0 = this.a - this.locX;
		double d2 = this.b - this.locY;
		final double d3 = this.c - this.locZ;
		final double d4 = d0 * d0 + d2 * d2 + d3 * d3;
		if(this.bu || d4 < 100.0 || d4 > 22500.0 || this.positionChanged || this.E) {
			this.attackTarget();
		}
		if(this.currentTarget != null) {
			this.a = this.currentTarget.locX;
			this.c = this.currentTarget.locZ;
			final double d5 = this.a - this.locX;
			final double d6 = this.c - this.locZ;
			final double d7 = Math.sqrt(d5 * d5 + d6 * d6);
			double d8 = 0.4000000059604645 + d7 / 80.0 - 1.0;
			if(d8 > 10.0) {
				d8 = 10.0;
			}
			this.b = this.currentTarget.getBoundingBox().b + d8;
		}
		else {
			this.a += this.random.nextGaussian() * 2.0;
			this.c += this.random.nextGaussian() * 2.0;
		}
		d2 /= MathHelper.sqrt(d0 * d0 + d3 * d3);
		final float f4 = 0.6f;
		d2 = MathHelper.a(d2, -f4, f4);
		this.motY += d2 * 0.10000000149011612;
		this.yaw = MathHelper.g(this.yaw);
		final double d9 = 180.0 - Math.atan2(d0, d3) * 180.0 / 3.1415927410125732;
		double d10 = MathHelper.g(d9 - this.yaw);
		if(d10 > 50.0) {
			d10 = 50.0;
		}
		if(d10 < -50.0) {
			d10 = -50.0;
		}
		final Vec3D vec3d = new Vec3D(this.a - this.locX, this.b - this.locY, this.c - this.locZ).a();
		double d8 = -MathHelper.cos(this.yaw * 3.1415927f / 180.0f);
		final Vec3D vec3d2 = new Vec3D(MathHelper.sin(this.yaw * 3.1415927f / 180.0f), this.motY, d8).a();
		float f5 = ((float)vec3d2.b(vec3d) + 0.5f) / 1.5f;
		if(f5 < 0.0f) {
			f5 = 0.0f;
		}
		this.aZ *= 0.8f;
		final float f6 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ) * 1.0f + 1.0f;
		double d11 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ) * 1.0 + 1.0;
		if(d11 > 40.0) {
			d11 = 40.0;
		}
		this.aZ += (float)(d10 * (0.699999988079071 / d11 / f6));
		this.yaw += this.aZ * 0.1f;
		final float f7 = (float)(2.0 / (d11 + 1.0));
		final float f8 = 0.06f;
		this.a(0.0f, -1.0f, f8 * (f5 * f7 + (1.0f - f7)));
		if(this.bv) {
			this.move(this.motX * 0.800000011920929, this.motY * 0.800000011920929, this.motZ * 0.800000011920929);
		}
		else {
			this.move(this.motX, this.motY, this.motZ);
		}
		final Vec3D vec3d3 = new Vec3D(this.motX, this.motY, this.motZ).a();
		float f9 = ((float)vec3d3.b(vec3d2) + 1.0f) / 2.0f;
		f9 = 0.8f + 0.15f * f9;
		this.motX *= f9;
		this.motZ *= f9;
		this.motY *= 0.9100000262260437;
		this.aG = this.yaw;
		final EntityComplexPart bl = this.bl;
		final EntityComplexPart bl2 = this.bl;
		final float n = 3.0f;
		bl2.length = n;
		bl.width = n;
		final EntityComplexPart bn = this.bn;
		final EntityComplexPart bn2 = this.bn;
		final float n2 = 2.0f;
		bn2.length = n2;
		bn.width = n2;
		final EntityComplexPart bo = this.bo;
		final EntityComplexPart bo2 = this.bo;
		final float n3 = 2.0f;
		bo2.length = n3;
		bo.width = n3;
		final EntityComplexPart bp = this.bp;
		final EntityComplexPart bp2 = this.bp;
		final float n4 = 2.0f;
		bp2.length = n4;
		bp.width = n4;
		this.bm.length = 3.0f;
		this.bm.width = 5.0f;
		this.bq.length = 2.0f;
		this.bq.width = 4.0f;
		this.br.length = 3.0f;
		this.br.width = 4.0f;
		final float f2 = (float)(this.b(5, 1.0f)[1] - this.b(10, 1.0f)[1]) * 10.0f / 180.0f * 3.1415927f;
		final float f3 = MathHelper.cos(f2);
		final float f10 = -MathHelper.sin(f2);
		final float f11 = this.yaw * 3.1415927f / 180.0f;
		final float f12 = MathHelper.sin(f11);
		final float f13 = MathHelper.cos(f11);
		this.bm.s_();
		this.bm.setPositionRotation(this.locX + f12 * 0.5f, this.locY, this.locZ - f13 * 0.5f, 0.0f, 0.0f);
		this.bq.s_();
		this.bq.setPositionRotation(this.locX + f13 * 4.5f, this.locY + 2.0, this.locZ + f12 * 4.5f, 0.0f, 0.0f);
		this.br.s_();
		this.br.setPositionRotation(this.locX - f13 * 4.5f, this.locY + 2.0, this.locZ - f12 * 4.5f, 0.0f, 0.0f);
		final double[] adouble = this.b(5, 1.0f);
		final double[] adouble2 = this.b(0, 1.0f);
		final float f45 = MathHelper.sin(this.yaw * 3.1415927f / 180.0f - this.aZ * 0.01f);
		final float f14 = MathHelper.cos(this.yaw * 3.1415927f / 180.0f - this.aZ * 0.01f);
		this.bl.s_();
		this.bl.setPositionRotation(this.locX + f45 * 5.5f * f3, this.locY + (adouble2[1] - adouble[1]) * 1.0 + f10 * 5.5f,
				this.locZ - f14 * 5.5f * f3, 0.0f, 0.0f);
		for(int j = 0; j < 3; ++j) {
			EntityComplexPart entitycomplexpart = null;
			if(j == 0) {
				entitycomplexpart = this.bn;
			}
			if(j == 1) {
				entitycomplexpart = this.bo;
			}
			if(j == 2) {
				entitycomplexpart = this.bp;
			}
			final double[] adouble3 = this.b(12 + j * 2, 1.0f);
			final float f15 = this.yaw * 3.1415927f / 180.0f + this.b(adouble3[0] - adouble[0]) * 3.1415927f / 180.0f * 1.0f;
			final float f16 = MathHelper.sin(f15);
			final float f17 = MathHelper.cos(f15);
			final float f18 = 1.5f;
			final float f19 = (j + 1) * 2.0f;
			entitycomplexpart.s_();
			entitycomplexpart.setPositionRotation(this.locX - (f12 * f18 + f16 * f19) * f3,
					this.locY + (adouble3[1] - adouble[1]) * 1.0 - (f19 + f18) * f10 + 1.5, this.locZ + (f13 * f18 + f17 * f19) * f3, 0.0f, 0.0f);
		}
		this.bv = (this.destroyBlocks(this.bl.getBoundingBox()) | this.destroyBlocks(this.bm.getBoundingBox()));
	}
	
	private void attackTarget() {
		if(currentTarget == null && target != null) {
			if(target.getGameMode() == GameMode.SURVIVAL) {
				owner.sendMessage(target.getName() + " has been killed, your dragon needs a new target!");
				target = null;
			}
			else this.currentTarget = ((CraftPlayer)target.getPlayer()).getHandle();
		}
		else {
			boolean isMinimumDistance;
			do {
				a = centerX + (this.random.nextFloat() * 120.0f - 60.0f);
				b = centerY + (this.random.nextFloat() * 100.0f - 40.0f);
				c = centerZ + (this.random.nextFloat() * 120.0f - 60.0f);
				double distanceX = locX - a, distanceY = locY - b, distanceZ = locZ - c;
				isMinimumDistance = (distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ > 60.0);
			}
			while(!isMinimumDistance);
			this.currentTarget = null;
		}
	}
	
	private float b(final double d0) {
		return (float)MathHelper.g(d0);
	}
	
	private boolean destroyBlocks(final AxisAlignedBB axisalignedbb) {
		int minX = MathHelper.floor(axisalignedbb.a), minY = MathHelper.floor(axisalignedbb.b), minZ = MathHelper.floor(axisalignedbb.c);
		int maxX = MathHelper.floor(axisalignedbb.d), maxY = MathHelper.floor(axisalignedbb.e), maxZ = MathHelper.floor(axisalignedbb.f);
		for(; minX <= maxX; minX++) {
			for(; minY <= maxY; minY++) {
				for(; minZ <= maxZ; minZ++) {
					BlockPosition bp = new BlockPosition(minX, minY, minZ);
					if(world.getType(bp).getBlock().getMaterial() != Material.AIR) world.setAir(bp);
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean a(final EntityComplexPart entitycomplexpart, final DamageSource damagesource, float f) {
		return true;
	}
	
	@Override
	public boolean damageEntity(final DamageSource damagesource, final float f) {
		return false;
	}
	
	@Override
	public void G() {}
	
	@Override
	protected void aY() {}
	
	@Override
	protected void D() {}
	
	@Override
	public Entity[] aC() {
		return this.children;
	}
	
	@Override
	public boolean ad() {
		return false;
	}
	
	@Override
	public World a() {
		return this.world;
	}
	
	@Override
	protected String z() {
		return "";
	}
	
	@Override
	protected String bn() {
		return "";
	}
	
	@Override
	protected float bA() {
		return 5.0f;
	}
	
	@Override
	public int getExpReward() {
		return 0;
	}
	
}
