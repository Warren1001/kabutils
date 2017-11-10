package com.kabryxis.kabutils.time;

import java.util.function.Consumer;

import com.kabryxis.kabutils.Worker;
import com.kabryxis.kabutils.concurrent.Threads;

public class Countdown {
	
	private CountdownManager cm;
	private long interval;
	private boolean repeat;
	private Consumer<Integer> timerAction;
	private Worker zeroAction;
	
	private int time = -1;
	private boolean wait = false, stop = false;
	
	public Countdown(CountdownManager cm, long interval, boolean repeat, Consumer<Integer> timerAction, Worker zeroAction) {
		this.cm = cm;
		this.interval = interval;
		this.repeat = repeat;
		this.timerAction = timerAction;
		this.zeroAction = zeroAction;
	}
	
	public void count(int t) {
		if(isCounting()) return;
		this.time = t;
		for(; time > 0; time--) {
			waitCheck();
			if(stop) break;
			timerAction.accept(time);
			Threads.sleep(interval);
		}
		if(!stop && zeroAction != null) zeroAction.work();
		time = -1;
		stop = false;
		cm.finished(this);
	}
	
	private void waitCheck() {
		if(stop) return;
		if(wait) {
			if(repeat) {
				while(wait) {
					if(stop) break;
					timerAction.accept(time);
					Threads.sleep(50);
				}
			}
			else {
				synchronized(this) {
					while(wait) {
						if(stop) break;
						Threads.wait(this);
					}
				}
			}
		}
	}
	
	public void setTime(int t) {
		this.time = t;
	}
	
	public void pause() {
		if(!wait) wait = true;
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
	
	public boolean isCounting() {
		return time != -1;
	}
	
}
