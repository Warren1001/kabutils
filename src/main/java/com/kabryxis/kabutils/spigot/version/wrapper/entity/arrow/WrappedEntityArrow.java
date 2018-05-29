package com.kabryxis.kabutils.spigot.version.wrapper.entity.arrow;

import com.kabryxis.kabutils.spigot.version.Version;
import com.kabryxis.kabutils.spigot.version.wrapper.Wrappable;
import com.kabryxis.kabutils.spigot.version.wrapper.entity.arrow.impl.WrappedEntityArrowv1_8_R3;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;

import java.util.function.Function;

public abstract class WrappedEntityArrow implements Wrappable {
	
	private static final Function<Arrow, WrappedEntityArrow> supplier;
	
	static {
		switch(Version.VERSION) {
			case v1_8_R3:
				supplier = WrappedEntityArrowv1_8_R3::new;
				break;
			default:
				supplier = null;
				break;
		}
	}
	
	public static WrappedEntityArrow newInstance(Arrow arrow) {
		return supplier.apply(arrow);
	}
	
	public abstract Block getHitBlock();
	
}
