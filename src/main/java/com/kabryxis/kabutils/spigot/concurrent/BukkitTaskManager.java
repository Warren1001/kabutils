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
	
	public static BukkitTask start(Runnable runnable, Plugin plugin) {
		return SCHEDULER.runTask(plugin, runnable);
	}
	
	public static BukkitTask start(Runnable runnable, Plugin plugin, long delay) {
		return SCHEDULER.runTaskLater(plugin, runnable, delay);
	}
	
	public static BukkitTask start(Runnable runnable, Plugin plugin, long delay, long interval) {
		return SCHEDULER.runTaskTimer(plugin, runnable, delay, interval);
	}
	
	public static BukkitTask start(BukkitRunnable runnable, Plugin plugin) {
		return runnable.runTask(plugin);
	}
	
	public static BukkitTask start(BukkitRunnable runnable, Plugin plugin, long delay) {
		return runnable.runTaskLater(plugin, delay);
	}
	
	public static BukkitTask start(BukkitRunnable runnable, Plugin plugin, long delay, long interval) {
		return runnable.runTaskTimer(plugin, delay, interval);
	}
	
	private final Plugin plugin;
	private final BukkitScheduler sch;
	
	public BukkitTaskManager(Plugin plugin) {
		this.plugin = plugin;
		this.sch = plugin.getServer().getScheduler();
	}
	
	public BukkitTask start(Runnable runnable) {
		return sch.runTask(plugin, runnable);
	}
	
	public BukkitTask start(Runnable runnable, long delay) {
		return sch.runTaskLater(plugin, runnable, delay);
	}
	
	public BukkitTask start(Runnable runnable, long delay, long interval) {
		return sch.runTaskTimer(plugin, runnable, delay, interval);
	}
	
	public BukkitTask start(BukkitRunnable runnable) {
		return runnable.runTask(plugin);
	}
	
	public BukkitTask start(BukkitRunnable runnable, long delay) {
		return runnable.runTaskLater(plugin, delay);
	}
	
	public BukkitTask start(BukkitRunnable runnable, long delay, long interval) {
		return runnable.runTaskTimer(plugin, delay, interval);
	}
	
}
