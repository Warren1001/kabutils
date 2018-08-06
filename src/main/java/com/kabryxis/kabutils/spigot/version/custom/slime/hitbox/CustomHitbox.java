package com.kabryxis.kabutils.spigot.version.custom.slime.hitbox;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntitySlime;
import net.minecraft.server.v1_8_R3.EntityTypes;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftSlime;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.lang.reflect.Field;
import java.util.Map;

public class CustomHitbox extends EntitySlime {
	
	public static void register() {
		String name = "Slime";
		Class<?> clazz = CustomHitbox.class;
		((Map<String, Class<?>>)getPrivateField("c")).put(name, clazz);
		((Map<Class<?>, String>)getPrivateField("d")).put(clazz, name);
		((Map<Class<?>, Integer>)getPrivateField("f")).put(clazz, 55);
	}
	
	private static Object getPrivateField(String fieldName) {
		Object o = null;
		try {
			Field field = EntityTypes.class.getDeclaredField(fieldName);
			field.setAccessible(true);
			o = field.get(null);
		}
		catch(NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return o;
	}
	
	public CustomHitbox(Location loc) {
		this(((CraftWorld)loc.getWorld()).getHandle(), loc.getX(), loc.getY(), loc.getZ());
	}
	
	public CustomHitbox(World world, double x, double y, double z) {
		super(world);
		setSize(2);
		setLocation(x, y, z, 0F, 0F);
		setInvisible(true);
		setCustomNameVisible(false);
		setCustomName("Kabryxis");
		world.addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);
	}
	
	@Override // move method
	public void t_() {}
	
	@Override
	public void collide(Entity entity) {}
	
	@Override
	public CraftSlime getBukkitEntity() {
		return (CraftSlime)super.getBukkitEntity();
	}
	
}
