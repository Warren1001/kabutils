package com.kabryxis.kabutils.spigot.concurrent;

import org.bukkit.scheduler.BukkitRunnable;

public abstract class TickingBukkitRunnable extends BukkitRunnable {
	
	private int tick = 0;
	
	@Override
	public void run() {
		tick(tick++);
	}
	
	public abstract void tick(int tick);
	
}
