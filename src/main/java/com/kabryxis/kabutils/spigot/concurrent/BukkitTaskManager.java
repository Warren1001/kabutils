package com.kabryxis.kabutils.spigot.concurrent;

import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class BukkitTaskManager {
	
	public static final String MAIN_THREAD_NAME = "Server thread";
	private static final BukkitScheduler SCHEDULER = Bukkit.getScheduler();
	
	public static boolean isMainThread() {
		return Thread.currentThread().getName().equals(MAIN_THREAD_NAME);
	}
	
	public static void validateIsMainThread() {
		Validate.isTrue(isMainThread(), "Asynchronous access on synchronous-required method");
	}
	
	public static BukkitTask start(Plugin plugin, Runnable runnable) {
		return SCHEDULER.runTask(plugin, runnable);
	}
	
	public static BukkitTask start(Plugin plugin, Runnable runnable, long delay) {
		return SCHEDULER.runTaskLater(plugin, runnable, delay);
	}
	
	public static BukkitTask start(Plugin plugin, Runnable runnable, long delay, long interval) {
		return SCHEDULER.runTaskTimer(plugin, runnable, delay, interval);
	}
	
	public static BukkitTask start(Plugin plugin, BukkitRunnable runnable) {
		return runnable.runTask(plugin);
	}
	
	public static BukkitTask start(Plugin plugin, BukkitRunnable runnable, long delay) {
		return runnable.runTaskLater(plugin, delay);
	}
	
	public static BukkitTask start(Plugin plugin, BukkitRunnable runnable, long delay, long interval) {
		return runnable.runTaskTimer(plugin, delay, interval);
	}
	
	private final Plugin plugin;
	
	public BukkitTaskManager(Plugin plugin) {
		this.plugin = plugin;
	}
	
	public BukkitTask start(Runnable runnable) {
		return start(plugin, runnable);
	}
	
	public BukkitTask start(Runnable runnable, long delay) {
		return start(plugin, runnable, delay);
	}
	
	public BukkitTask start(Runnable runnable, long delay, long interval) {
		return start(plugin, runnable, delay, interval);
	}
	
	public BukkitTask start(BukkitRunnable runnable) {
		return start(plugin, runnable);
	}
	
	public BukkitTask start(BukkitRunnable runnable, long delay) {
		return start(plugin, runnable, delay);
	}
	
	public BukkitTask start(BukkitRunnable runnable, long delay, long interval) {
		return start(plugin, runnable, delay, interval);
	}
	
}
