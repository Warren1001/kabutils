package com.kabryxis.kabutils.spigot.version.wrapper.entity.human;

import com.kabryxis.kabutils.spigot.version.wrapper.Wrappable;
import com.kabryxis.kabutils.spigot.version.wrapper.WrapperFactory;
import com.kabryxis.kabutils.spigot.version.wrapper.entity.human.impl.WrappedEntityHumanv1_8_R3;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

public interface WrappedEntityHuman extends Wrappable {
	
	Class<WrappedEntityHumanv1_8_R3> v1_8_R3 = WrappedEntityHumanv1_8_R3.class;
	
	static WrappedEntityHuman newCloneInstance(Player player) {
		return WrapperFactory.get(WrappedEntityHuman.class, Player.class, player);
	}
	
	Object getDataWatcher();
	
	HumanEntity getBukkitEntity();
	
}
