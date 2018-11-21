package com.kabryxis.kabutils.spigot.version.wrapper.entity.arrow.impl;

import com.kabryxis.kabutils.spigot.version.wrapper.entity.arrow.WrappedEntityArrow;
import net.minecraft.server.v1_8_R3.EntityArrow;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArrow;
import org.bukkit.entity.Arrow;

public class WrappedEntityArrowv1_8_R3 implements WrappedEntityArrow {
	
	private EntityArrow entityArrow;
	
	public WrappedEntityArrowv1_8_R3(Arrow arrow) {
		setHandle(arrow);
	}
	
	@Override
	public WrappedEntityArrowv1_8_R3 setHandle(Object obj) {
		if(obj instanceof EntityArrow) entityArrow = (EntityArrow)obj;
		else if(obj instanceof Arrow) entityArrow = ((CraftArrow)obj).getHandle();
		return this;
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
			int y = WrappedEntityArrow.FIELD_Y.getInt(entityArrow);
			if(y == -1) return null;
			return entityArrow.world.getWorld().getBlockAt(WrappedEntityArrow.FIELD_X.getInt(entityArrow), y, WrappedEntityArrow.FIELD_Z.getInt(entityArrow));
		} catch(IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
