package com.kabryxis.kabutils.spigot.concurrent;

import com.kabryxis.kabutils.concurrent.Threads;
import com.kabryxis.kabutils.concurrent.thread.PausableThread;

import java.util.Set;
import java.util.function.Consumer;

public class BulkActionThread<T> extends PausableThread {
	
	private final Set<T> objects;
	private final Consumer<T> action;
	private final long interval;
	
	public BulkActionThread(String name, Set<T> objects, Consumer<T> action, long interval) {
		super(name);
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
			BukkitThreads.sync(() -> {
				synchronized(objects) {
					objects.forEach(action);
					objects.clear();
				}
			});
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
