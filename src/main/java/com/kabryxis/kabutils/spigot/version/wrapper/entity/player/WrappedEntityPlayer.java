package com.kabryxis.kabutils.spigot.version.wrapper.entity.player;

import com.kabryxis.kabutils.spigot.version.Version;
import com.kabryxis.kabutils.spigot.version.wrapper.Wrappable;
import com.kabryxis.kabutils.spigot.version.wrapper.WrapperFactory;
import com.kabryxis.kabutils.spigot.version.wrapper.entity.player.impl.WrappedEntityPlayerv1_13_R2;
import com.kabryxis.kabutils.spigot.version.wrapper.entity.player.impl.WrappedEntityPlayerv1_8_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.entity.player.impl.WrappedEntityPlayerv1_8_R3;
import com.kabryxis.kabutils.spigot.version.wrapper.packet.WrappedPacket;
import com.kabryxis.kabutils.utility.ReflectionHelper;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public interface WrappedEntityPlayer extends Wrappable {
	
	Class<WrappedEntityPlayerv1_8_R1> v1_8_R1 = WrappedEntityPlayerv1_8_R1.class;
	Class<WrappedEntityPlayerv1_8_R3> v1_8_R3 = WrappedEntityPlayerv1_8_R3.class;
	Class<WrappedEntityPlayerv1_13_R2> v1_13_R2 = WrappedEntityPlayerv1_13_R2.class;
	Field FIELD_CONTAINER_COUNTER = ReflectionHelper.getField(Version.getNMSClass("EntityPlayer"), "containerCounter");
	
	static WrappedEntityPlayer newInstance(Player player) {
		return WrapperFactory.getSupplier(WrappedEntityPlayer.class, Object.class).apply(player);
	}
	
	static WrappedEntityPlayer newInstance() {
		return newInstance(null);
	}
	
	@Override
	WrappedEntityPlayer setHandle(Object obj);
	
	void sendPacket(WrappedPacket packet);
	
	void teleportRelative(Location location);
	
	int getContainerId();
	
	int getCurrentContainerCounter();
	
	double getLocX();
	
	double getLocY();
	
	double getLocZ();
	
	int getPing();
	
	boolean isFireProof();
	
}
