package com.kabryxis.kabutils.concurrent.thread;

import java.util.HashSet;
import java.util.Set;

public abstract class QuittableThread extends Thread {
	
	static {
		Runtime.getRuntime().addShutdownHook(new Thread(QuittableThread::stopAllThreads));
	}
	
	private static final Set<QuittableThread> QUITTABLE_THREADS = new HashSet<>();
	
	public static void stopAllThreads() {
		QUITTABLE_THREADS.forEach(QuittableThread::quit);
		QUITTABLE_THREADS.clear();
	}
	
	private boolean stop = true;
	
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
	
	@Override
	public synchronized void start() {
		stop = false;
		super.start();
	}
	
	private void register() {
		QUITTABLE_THREADS.add(this);
	}
	
	public void quit() {
		stop = true;
	}
	
	public boolean isRunning() {
		return !stop;
	}
	
}
