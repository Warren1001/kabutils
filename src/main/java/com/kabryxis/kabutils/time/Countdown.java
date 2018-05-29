package com.kabryxis.kabutils.time;

import com.kabryxis.kabutils.Worker;
import com.kabryxis.kabutils.concurrent.Threads;
import com.kabryxis.kabutils.spigot.utility.BiIntConsumer;

public class Countdown {
	
	private String name;
	private long interval;
	private boolean repeat;
	private BiIntConsumer<TimeLeft> timerAction;
	private Worker zeroAction;
	
	private Thread currThread;
	private int time = -1, maxTime = -1;
	private boolean wait = false, stop = false;
	
	public Countdown(String name, long interval, boolean repeat, BiIntConsumer<TimeLeft> timerAction, Worker zeroAction) {
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
			timerAction.accept(time, getTimeLeft());
			Threads.sleep(interval);
		}
		if(!stop && zeroAction != null) zeroAction.work();
		currThread = null;
		time = -1;
		stop = false;
	}
	
	private TimeLeft getTimeLeft() {
		if(maxTime == time) return TimeLeft.FULL;
		if((int)Math.ceil((double)maxTime * 0.75) == time) return TimeLeft.THREE_FOURTHS;
		if((int)Math.ceil((double)maxTime * 0.66) == time) return TimeLeft.TWO_THIRDS;
		if((int)Math.ceil((double)maxTime * 0.5) == time) return TimeLeft.HALF;
		if((int)Math.ceil((double)maxTime * 0.33) == time) return TimeLeft.ONE_THIRD;
		if((int)Math.ceil((double)maxTime * 0.25) == time) return TimeLeft.ONE_FORTH;
		return TimeLeft.UNKNOWN;
	}
	
	private void waitCheck() {
		if(wait) {
			if(repeat) {
				TimeLeft timeLeft = getTimeLeft();
				while(wait) {
					timerAction.accept(time, timeLeft);
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
	
	public void setTime(int time) {
		setCurrentTime(time);
		this.maxTime = time;
	}
	
	public void setCurrentTime(int time) {
		this.time = time;
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
