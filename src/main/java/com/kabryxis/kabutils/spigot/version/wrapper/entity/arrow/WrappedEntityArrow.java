package com.kabryxis.kabutils.spigot.version.wrapper.entity.arrow;

import com.kabryxis.kabutils.spigot.version.Version;
import com.kabryxis.kabutils.spigot.version.wrapper.Wrappable;
import com.kabryxis.kabutils.spigot.version.wrapper.WrapperFactory;
import com.kabryxis.kabutils.spigot.version.wrapper.entity.arrow.impl.WrappedEntityArrowv1_8_R3;
import com.kabryxis.kabutils.utility.ReflectionHelper;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;

import java.lang.reflect.Field;

public interface WrappedEntityArrow extends Wrappable {
	
	Class<WrappedEntityArrowv1_8_R3> v1_8_R3 = WrappedEntityArrowv1_8_R3.class;
	Field FIELD_X = ReflectionHelper.getField(Version.getNMSClass("EntityArrow"), "d");
	Field FIELD_Y = ReflectionHelper.getField(Version.getNMSClass("EntityArrow"), "e");
	Field FIELD_Z = ReflectionHelper.getField(Version.getNMSClass("EntityArrow"), "f");
	
	static WrappedEntityArrow newInstance(Arrow arrow) {
		return WrapperFactory.getSupplier(WrappedEntityArrow.class, Arrow.class).apply(arrow);
	}
	
	@Override
	WrappedEntityArrow setHandle(Object obj);
	
	Block getHitBlock();
	
}
