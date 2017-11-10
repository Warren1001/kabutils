package com.kabryxis.kabutils.concurrent.thread;

import com.kabryxis.kabutils.concurrent.Threads;

public abstract class PausableThread extends QuittableThread {
	
	private boolean pause = false;
	
	public PausableThread() {
		super();
	}
	
	public PausableThread(String name) {
		super(name);
	}
	
	public PausableThread(ThreadGroup group, String name) {
		super(group, name);
	}
	
	public synchronized void pause() {
		if(!pause) {
			pause = true;
			onPause();
		}
	}
	
	public synchronized void unpause() {
		if(pause) {
			pause = false;
			notify();
			onUnpause();
		}
	}
	
	public boolean isPaused() {
		return pause;
	}
	
	@Override
	public void run() {
		begin();
		while(canTick0()) {
			if(pause) {
				while(pause) {
					if(!isRunning()) break;
					Threads.wait(this);
				}
			}
			if(!isRunning()) break;
			tick();
		}
		end();
	}
	
	public abstract void onPause();
	
	public abstract void onUnpause();
	
}
