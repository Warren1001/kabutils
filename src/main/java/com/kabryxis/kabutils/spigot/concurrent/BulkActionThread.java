package com.kabryxis.kabutils.spigot.concurrent;

import com.kabryxis.kabutils.concurrent.Threads;
import com.kabryxis.kabutils.concurrent.thread.PausableThread;
import org.bukkit.plugin.Plugin;

import java.util.Set;
import java.util.function.Consumer;

public class BulkActionThread<T> extends PausableThread {
	
	private final Plugin plugin;
	private final Set<T> objects;
	private final Consumer<T> action;
	private final long interval;
	
	public BulkActionThread(String name, Plugin plugin, Set<T> objects, Consumer<T> action, long interval) {
		super(name);
		this.plugin = plugin;
		this.objects = objects;
		this.action = action;
		this.interval = interval;
	}
	
	public void add(T object) {
		synchronized(objects) {
			objects.add(object);
		}
	}
	
	@Override
	public void run() {
		while(isRunning()) {
			pauseCheck();
			BukkitTaskManager.start(this::clear, plugin);
			Threads.sleep(interval);
		}
		clear();
	}
	
	public void clear() {
		synchronized(objects) {
			objects.forEach(action);
			objects.clear();
		}
	}
	
	@Override
	public void onPause() {
		clear();
	}
	
	@Override
	public void onUnpause() {}
	
}
