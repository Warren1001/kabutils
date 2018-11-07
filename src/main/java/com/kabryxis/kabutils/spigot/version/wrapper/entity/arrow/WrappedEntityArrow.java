package com.kabryxis.kabutils.spigot.version.wrapper.entity.arrow;

import com.kabryxis.kabutils.spigot.version.wrapper.Wrappable;
import com.kabryxis.kabutils.spigot.version.wrapper.WrapperFactory;
import com.kabryxis.kabutils.spigot.version.wrapper.entity.arrow.impl.WrappedEntityArrowv1_8_R3;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;

public interface WrappedEntityArrow extends Wrappable {
	
	Class<WrappedEntityArrowv1_8_R3> v1_8_R3 = WrappedEntityArrowv1_8_R3.class;
	
	static WrappedEntityArrow newInstance(Arrow arrow) {
		return WrapperFactory.getSupplier(WrappedEntityArrow.class, Arrow.class).apply(arrow);
	}
	
	Block getHitBlock();
	
}
