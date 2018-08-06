package com.kabryxis.kabutils.spigot.version.wrapper.entity.human;

import com.kabryxis.kabutils.spigot.version.Version;
import com.kabryxis.kabutils.spigot.version.wrapper.Wrappable;
import com.kabryxis.kabutils.spigot.version.wrapper.entity.human.impl.WrappedEntityHumanv1_8_R3;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.function.Function;

public abstract class WrappedEntityHuman implements Wrappable {
	
	private static final Function<Player, WrappedEntityHuman> cloneSupplier;
	
	static {
		switch(Version.VERSION) {
			case v1_8_R3:
				cloneSupplier = WrappedEntityHumanv1_8_R3::new;
				break;
			default:
				cloneSupplier = null;
				break;
		}
	}
	
	public static WrappedEntityHuman newCloneInstance(Player player) {
		return cloneSupplier.apply(player);
	}
	
	public abstract Object getDataWatcher();
	
	public abstract HumanEntity getBukkitEntity();
	
}
