package com.kabryxis.kabutils.concurrent.thread;

import java.util.Queue;

public class ActionThread extends PausableThread {
	
	private final Queue<Runnable> queue;
	
	private long lastQueueTime = 0L;
	
	public ActionThread(Queue<Runnable> queue) {
		super();
		this.queue = queue;
	}
	
	public ActionThread(String name, Queue<Runnable> queue) {
		super(name);
		this.queue = queue;
	}
	
	public ActionThread(ThreadGroup group, String name, Queue<Runnable> queue) {
		super(group, name);
		this.queue = queue;
	}
	
	@Override
	public void run() {
		lastQueueTime = System.currentTimeMillis();
		while(isRunning()) {
			pauseCheck();
			Runnable run;
			while((run = queue.poll()) != null) {
				run.run();
			}
			if(shouldPause()) pause();
		}
	}
	
	public void queue(Runnable action) {
		unpause();
		queue.add(action);
		lastQueueTime = System.currentTimeMillis();
	}
	
	private boolean shouldPause() {
		return System.currentTimeMillis() - lastQueueTime > 10000;
	}
	
	@Override
	public void onPause() {}
	
	@Override
	public void onUnpause() {}
	
}
