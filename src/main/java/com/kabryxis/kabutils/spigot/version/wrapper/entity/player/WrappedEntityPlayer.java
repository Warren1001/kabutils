package com.kabryxis.kabutils.spigot.version.wrapper.entity.player;

import com.kabryxis.kabutils.spigot.version.wrapper.Wrappable;
import com.kabryxis.kabutils.spigot.version.wrapper.WrapperFactory;
import com.kabryxis.kabutils.spigot.version.wrapper.entity.player.impl.WrappedEntityPlayerv1_8_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.entity.player.impl.WrappedEntityPlayerv1_8_R3;
import com.kabryxis.kabutils.spigot.version.wrapper.packet.WrappedPacket;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface WrappedEntityPlayer extends Wrappable {
	
	Class<WrappedEntityPlayerv1_8_R1> v1_8_R1 = WrappedEntityPlayerv1_8_R1.class;
	Class<WrappedEntityPlayerv1_8_R3> v1_8_R3 = WrappedEntityPlayerv1_8_R3.class;
	
	static WrappedEntityPlayer newInstance(Player player) {
		return WrapperFactory.get(WrappedEntityPlayer.class, Object.class, player);
	}
	
	static WrappedEntityPlayer newInstance() {
		return newInstance(null);
	}
	
	void sendPacket(WrappedPacket packet);
	
	void teleportRelative(Location location);
	
	int getContainerId();
	
	int getCurrentContainerCounter();
	
	double getLocX();
	
	double getLocY();
	
	double getLocZ();
	
	int getPing();
	
}
