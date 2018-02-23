package com.kabryxis.kabutils.time;

import com.kabryxis.kabutils.Worker;
import com.kabryxis.kabutils.concurrent.Threads;

import java.util.function.IntConsumer;

public class Countdown {
	
	private String name;
	private long interval;
	private boolean repeat;
	private IntConsumer timerAction;
	private Worker zeroAction;
	
	private Thread currThread;
	private int time = -1;
	private boolean wait = false, stop = false;
	
	public Countdown(String name, long interval, boolean repeat, IntConsumer timerAction, Worker zeroAction) {
		this.name = name;
		this.interval = interval;
		this.repeat = repeat;
		this.timerAction = timerAction;
		this.zeroAction = zeroAction;
	}
	
	public String getName() {
		return name;
	}
	
	public void count(int t) {
		if(isActive()) return;
		currThread = Thread.currentThread();
		setTime(t);
		for(; time > 0 && !stop; time--) {
			waitCheck();
			timerAction.accept(time);
			Threads.sleep(interval);
		}
		if(!stop && zeroAction != null) zeroAction.work();
		currThread = null;
		time = -1;
		stop = false;
	}
	
	private void waitCheck() {
		if(wait) {
			if(repeat) {
				while(wait) {
					timerAction.accept(time);
					Threads.sleep(interval);
				}
			}
			else {
				synchronized(this) {
					while(wait) {
						Threads.wait(this);
					}
				}
			}
		}
	}
	
	public void setTime(int t) {
		this.time = t;
	}
	
	public Thread getCurrentlyActiveThread() {
		return currThread;
	}
	
	public void pause() {
		if(!wait && isActive()) wait = true;
	}
	
	public void unpause() {
		if(wait) {
			wait = false;
			if(!repeat) {
				synchronized(this) {
					notify();
				}
			}
		}
	}
	
	public void stop() {
		if(!stop) stop = true;
	}
	
	public boolean isPaused() {
		return wait;
	}
	
	public boolean isActive() {
		return currThread != null;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Countdown && ((Countdown)obj).getName().equals(name);
	}
	
}
