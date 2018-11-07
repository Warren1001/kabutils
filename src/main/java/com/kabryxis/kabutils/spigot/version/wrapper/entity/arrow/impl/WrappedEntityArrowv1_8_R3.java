package com.kabryxis.kabutils.spigot.version.wrapper.entity.arrow.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.entity.arrow.WrappedEntityArrow;
import net.minecraft.server.v1_8_R3.EntityArrow;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArrow;
import org.bukkit.entity.Arrow;

import java.lang.reflect.Field;

public class WrappedEntityArrowv1_8_R3 implements WrappedEntityArrow {
	
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
	
	private EntityArrow entityArrow;
	
	public WrappedEntityArrowv1_8_R3(Arrow arrow) {
		setHandle(arrow);
	}
	
	@Override
	public void setHandle(Object obj) {
		if(obj instanceof EntityArrow) entityArrow = (EntityArrow)obj;
		else if(obj instanceof Arrow) entityArrow = ((CraftArrow)obj).getHandle();
	}
	
	@Override
	public EntityArrow getHandle() {
		return entityArrow;
	}
	
	@Override
	public void clear() {
		entityArrow = null;
	}
	
	@Override
	public Block getHitBlock() {
		try {
			int y = fieldY.getInt(entityArrow);
			if(y == -1) return null;
			return entityArrow.world.getWorld().getBlockAt(fieldX.getInt(entityArrow), y, fieldZ.getInt(entityArrow));
		} catch(IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
