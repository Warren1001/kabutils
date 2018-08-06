package com.kabryxis.kabutils.spigot.version.wrapper.entity.player;

import com.kabryxis.kabutils.spigot.version.Version;
import com.kabryxis.kabutils.spigot.version.wrapper.Wrappable;
import com.kabryxis.kabutils.spigot.version.wrapper.entity.player.impl.WrappedEntityPlayerv1_8_R1;
import com.kabryxis.kabutils.spigot.version.wrapper.entity.player.impl.WrappedEntityPlayerv1_8_R3;
import com.kabryxis.kabutils.spigot.version.wrapper.packet.WrappedPacket;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class WrappedEntityPlayer implements Wrappable {
	
	private static final Function<Player, WrappedEntityPlayer> playerSupplier;
	private static final Supplier<WrappedEntityPlayer> supplier;
	
	static {
		switch(Version.VERSION) {
			case v1_8_R1:
				playerSupplier = WrappedEntityPlayerv1_8_R1::new;
				supplier = WrappedEntityPlayerv1_8_R1::new;
				break;
			case v1_8_R3:
				playerSupplier = WrappedEntityPlayerv1_8_R3::new;
				supplier = WrappedEntityPlayerv1_8_R3::new;
				break;
			default:
				playerSupplier = null;
				supplier = null;
				break;
		}
	}
	
	public static WrappedEntityPlayer newInstance(Player player) {
		return playerSupplier.apply(player);
	}
	
	public static WrappedEntityPlayer newInstance() {
		return supplier.get();
	}
	
	public abstract void setPlayer(Player player);
	
	public abstract void sendPacket(WrappedPacket packet);
	
	public abstract void teleportRelative(Location location);
	
	public abstract int getContainerId();
	
	public abstract int getCurrentContainerCounter();
	
	public abstract double getLocX();
	
	public abstract double getLocY();
	
	public abstract double getLocZ();
	
}
