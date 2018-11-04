package com.kabryxis.kabutils.spigot.concurrent;

import com.kabryxis.kabutils.concurrent.Threads;
import com.kabryxis.kabutils.concurrent.thread.PausableThread;
import org.bukkit.plugin.Plugin;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DelayedActionThread extends PausableThread {
	
	protected final Plugin plugin;
	protected final Set<DelayedAction> active;
	protected final Runnable action;
	
	public DelayedActionThread(String name, Plugin plugin, Set<DelayedAction> actionSet, Runnable action) {
		super(name);
		this.plugin = plugin;
		this.active = actionSet;
		this.action = action;
	}
	
	public DelayedActionThread(String name, Plugin plugin, Set<DelayedAction> actionSet) {
		this(name, plugin, actionSet, () -> {
			synchronized(actionSet) {
				actionSet.removeIf(DelayedAction::test);
			}
		});
	}
	
	public DelayedActionThread(String name, Plugin plugin) {
		this(name, plugin, ConcurrentHashMap.newKeySet());
	}
	
	public void add(DelayedAction action) {
		synchronized(active) {
			active.add(action);
		}
	}
	
	@Override
	public void run() {
		while(isRunning()) {
			pauseCheck();
			BukkitTaskManager.start(plugin, action);
			Threads.sleep(50);
		}
		clear();
	}
	
	public void clear() {
		synchronized(active) {
			active.clear();
		}
	}
	
	@Override
	public void onPause() {
		clear();
	}
	
	@Override
	public void onUnpause() {}
	
}
