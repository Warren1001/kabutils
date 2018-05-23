package com.kabryxis.kabutils.spigot.version.wrapper.entity.arrow.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.entity.arrow.WrappedEntityArrow;
import net.minecraft.server.v1_8_R3.EntityArrow;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArrow;
import org.bukkit.entity.Arrow;

import java.lang.reflect.Field;

public class WrappedEntityArrowv1_8_R3 extends WrappedEntityArrow<EntityArrow> {
	
	private static Field fieldX, fieldY, fieldZ;
	
	static {
		try {
			fieldX = EntityArrow.class.getDeclaredField("d");
			fieldY = EntityArrow.class.getDeclaredField("e");
			fieldZ = EntityArrow.class.getDeclaredField("f");
			fieldX.setAccessible(true);
			fieldY.setAccessible(true);
			fieldZ.setAccessible(true);
		} catch(NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void set(Arrow arrow) {
		set(((CraftArrow)arrow).getHandle());
	}
	
	@Override
	public Block getHitBlock() {
		try {
			int y = fieldY.getInt(get());
			if(y == -1) return null;
			return get().world.getWorld().getBlockAt(fieldX.getInt(get()), y, fieldZ.getInt(get()));
		} catch(IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
