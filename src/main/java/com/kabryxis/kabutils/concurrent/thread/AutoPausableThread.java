package com.kabryxis.kabutils.concurrent.thread;

import com.kabryxis.kabutils.concurrent.Threads;

public abstract class AutoPausableThread extends PausableThread {
	
	private final long delay;
	private final long interval;
	
	public AutoPausableThread() {
		super();
		this.delay = 50;
		this.interval = 200;
	}
	
	public AutoPausableThread(long delay, long interval) {
		super();
		this.delay = delay / interval;
		this.interval = interval;
	}
	
	public AutoPausableThread(String name) {
		super(name);
		this.delay = 50;
		this.interval = 200;
	}
	
	public AutoPausableThread(String name, long delay, long interval) {
		super(name);
		this.delay = delay / interval;
		this.interval = interval;
	}
	
	public AutoPausableThread(ThreadGroup group, String name) {
		super(group, name);
		this.delay = 50L;
		this.interval = 200;
	}
	
	public AutoPausableThread(ThreadGroup group, String name, long delay, long interval) {
		super(group, name);
		this.delay = delay / interval;
		this.interval = interval;
	}
	
	@Override
	public void run() {
		while(canTick0()) {
			while(isPaused()) {
				if(!isRunning()) break;
				Threads.wait(this);
			}
			
			tick();
			
			if(shouldPause()) {
				if(!isRunning()) continue;
				boolean pause = true;
				for(int i = 0; i < delay; i++) {
					if(shouldPause()) {
						pause = false;
						continue;
					}
					Threads.sleep(interval);
				}
				if(pause && shouldPause()) pause();
			}
		}
	}
	
	protected abstract boolean shouldPause();
	
}
