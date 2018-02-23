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
		if(!pause) {
			pause = true;
			onPause();
		}
	}
	
	public void unpause() {
		if(pause) {
			pause = false;
			onUnpause();
			synchronized(this) {
				notify();
			}
		}
	}
	
	public boolean isPaused() {
		return pause;
	}
	
	public abstract void onPause();
	
	public abstract void onUnpause();
	
	public void pauseCheck() {
		if(pause) {
			synchronized(this) {
				while(pause) {
					Threads.wait(this);
				}
			}
		}
	}
	
}
