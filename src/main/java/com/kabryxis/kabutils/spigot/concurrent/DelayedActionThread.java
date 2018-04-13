package com.kabryxis.kabutils.spigot.concurrent;

import com.kabryxis.kabutils.concurrent.Threads;
import com.kabryxis.kabutils.concurrent.thread.PausableThread;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DelayedActionThread extends PausableThread {
	
	protected final Set<DelayedAction> active;
	protected final Runnable action;
	
	public DelayedActionThread(String name, Set<DelayedAction> actionSet, Runnable action) {
		super(name);
		this.active = actionSet;
		this.action = action;
	}
	
	public DelayedActionThread(String name, Set<DelayedAction> actionSet) {
		this(name, actionSet, () -> {
			synchronized(actionSet) {
				actionSet.removeIf(DelayedAction::test);
			}
		});
	}
	
	public DelayedActionThread(String name) {
		this(name, ConcurrentHashMap.newKeySet());
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
			BukkitThreads.sync(action);
			Threads.sleep(50);
		}
		clear();
	}
	
	public void clear() {
		synchronized(active) {
			active.forEach(DelayedAction::cache);
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
