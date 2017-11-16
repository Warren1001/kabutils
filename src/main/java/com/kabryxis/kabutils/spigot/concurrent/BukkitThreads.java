package com.kabryxis.kabutils.spigot.concurrent;

import org.apache.commons.lang3.Validate;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import com.kabryxis.kabutils.spigot.plugin.SpoofPlugin;

public class BukkitThreads {
	
	private static final String mainThreadName = "Server thread";
	private static final Plugin plugin = SpoofPlugin.get();
	private static final BukkitScheduler sch = plugin.getServer().getScheduler();
	
	public static boolean isMainThread() {
		return Thread.currentThread().getName().equals(mainThreadName);
	}
	
	public static void validateIsMainThread() {
		Validate.isTrue(isMainThread(), "Asynchronous access on synchronous-required method");
	}
	
	public static BukkitTask sync(Runnable runnable) {
		return sch.runTask(plugin, runnable);
	}
	
	public static BukkitTask syncLater(Runnable runnable, long delay) {
		return sch.runTaskLater(plugin, runnable, delay);
	}
	
	public static BukkitTask syncTimer(Runnable runnable, long delay, long interval) {
		return sch.runTaskTimer(plugin, runnable, delay, interval);
	}
	
	public static BukkitTask sync(BukkitRunnable runnable) {
		return runnable.runTask(plugin);
	}
	
	public static BukkitTask syncLater(BukkitRunnable runnable, long delay) {
		return runnable.runTaskLater(plugin, delay);
	}
	
	public static BukkitTask syncTimer(BukkitRunnable runnable, long delay, long interval) {
		return runnable.runTaskTimer(plugin, delay, interval);
	}
	
}
