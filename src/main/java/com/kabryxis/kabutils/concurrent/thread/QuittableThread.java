package com.kabryxis.kabutils.concurrent.thread;

import com.kabryxis.kabutils.concurrent.Threads;

public abstract class QuittableThread extends Thread {
	
	private boolean stop = false;
	
	public QuittableThread() {
		super();
		register();
	}
	
	public QuittableThread(String name) {
		super(name);
		register();
	}
	
	public QuittableThread(ThreadGroup group, String name) {
		super(group, name);
		register();
	}
	
	private void register() {
		Threads.registerQuittable(this);
	}
	
	public void quit() {
		stop = true;
	}
	
	public boolean isRunning() {
		return !stop;
	}
	
}
