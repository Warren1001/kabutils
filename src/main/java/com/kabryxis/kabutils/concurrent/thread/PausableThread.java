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
	
	public void pause() {
		if(!pause) pause = true;
	}
	
	public synchronized void unpause() {
		if(pause) {
			pause = false;
			onUnpause();
			notify();
		}
	}
	
	public boolean isPaused() {
		return pause;
	}
	
	public abstract void onPause();
	
	public abstract void onUnpause();
	
	protected synchronized boolean pause0() {
		if(!isRunning()) return false;
		if(pause) {
			onPause();
			while(pause) {
				if(!isRunning()) break;
				Threads.wait(this);
			}
		}
		return true;
	}
	
}
