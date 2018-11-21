package com.kabryxis.kabutils.time;

import com.kabryxis.kabutils.utility.BiIntConsumer;

import java.util.HashMap;
import java.util.Map;

public class CountdownManager {
	
	private final Map<String, Countdown> countdowns = new HashMap<>();
	private final Map<Thread, Countdown> currentlyActive = new HashMap<>();
	private Countdown lastActivated = null;
	
	public Countdown constructNewCountdown(String name, long interval, boolean repeat, BiIntConsumer<TimeLeft> timerAction, Runnable zeroAction) {
		return countdowns.computeIfAbsent(name, s -> new Countdown(s, interval, repeat, timerAction, zeroAction));
	}
	
	public Countdown constructNewCountdown(String name, long interval, boolean repeat, BiIntConsumer<TimeLeft> timerAction) {
		return constructNewCountdown(name, interval, repeat, timerAction, null);
	}
	
	public Countdown constructNewCountdown(String name, long interval, BiIntConsumer<TimeLeft> timerAction, Runnable zeroAction) {
		return constructNewCountdown(name, interval, false, timerAction, zeroAction);
	}
	
	public Countdown constructNewCountdown(String name, long interval, BiIntConsumer<TimeLeft> timerAction) {
		return constructNewCountdown(name, interval, timerAction, null);
	}
	
	public Countdown getCountdown(String name) {
		return countdowns.get(name);
	}
	
	public Countdown getCurrentlyActive(Thread thread) {
		return currentlyActive.get(thread);
	}
	
	public Countdown getLastActivated() {
		return lastActivated;
	}
	
	public void count(String name, int time) {
		Countdown cd = countdowns.get(name);
		if(cd != null && !cd.isActive()) {
			Thread currThread = Thread.currentThread();
			currentlyActive.put(currThread, cd);
			lastActivated = cd;
			cd.count(time);
			currentlyActive.remove(currThread);
		}
	}
	
	public void pauseAll() {
		countdowns.values().forEach(Countdown::pause);
	}
	
	public void unpauseAll() {
		countdowns.values().forEach(Countdown::unpause);
	}
	
	public void pause(String name) {
		Countdown cd = countdowns.get(name);
		if(cd != null) cd.pause();
	}
	
	public void unpause(String name) {
		Countdown cd = countdowns.get(name);
		if(cd != null) cd.unpause();
	}
	
	public void setCurrentTime(String name, int time) {
		Countdown cd = countdowns.get(name);
		if(cd != null) cd.setCurrentTime(time);
	}
	
}
