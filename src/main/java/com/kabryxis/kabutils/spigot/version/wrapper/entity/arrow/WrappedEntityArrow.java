package com.kabryxis.kabutils.spigot.version.wrapper.entity.arrow;

import com.kabryxis.kabutils.spigot.version.wrapper.Wrapper;
import com.kabryxis.kabutils.spigot.version.wrapper.entity.arrow.impl.WrappedEntityArrowv1_8_R3;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;

public abstract class WrappedEntityArrow<T> extends Wrapper<T> {
	
	static { // include in maven shade plugin
		WrappedEntityArrowv1_8_R3.class.getClass();
	}
	
	public abstract void set(Arrow arrow);
	
	public abstract Block getHitBlock();
	
}
