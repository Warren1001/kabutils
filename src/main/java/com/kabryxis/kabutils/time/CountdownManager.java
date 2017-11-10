package com.kabryxis.kabutils.time;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import com.kabryxis.kabutils.Worker;

public class CountdownManager {
	
	private final Map<String, Countdown> countdowns = new HashMap<>();
	private final Map<Thread, Countdown> currentlyActive = new HashMap<>();
	
	public Countdown constructNewCountdown(String name, long interval, boolean repeat, Consumer<Integer> timerAction, Worker zeroAction) {
		return countdowns.computeIfAbsent(name, s -> new Countdown(this, interval, repeat, timerAction, zeroAction));
	}
	
	public Countdown constructNewCountdown(String name, long interval, boolean repeat, Consumer<Integer> timerAction) {
		return constructNewCountdown(name, interval, repeat, timerAction, null);
	}
	
	public Countdown constructNewCountdown(String name, long interval, Consumer<Integer> timerAction, Worker zeroAction) {
		return constructNewCountdown(name, interval, false, timerAction, zeroAction);
	}
	
	public Countdown constructNewCountdown(String name, long interval, Consumer<Integer> timerAction) {
		return constructNewCountdown(name, interval, timerAction, null);
	}
	
	public Countdown getCountdown(String name) {
		return countdowns.get(name);
	}
	
	public Countdown getCurrentlyActive(Thread thread) {
		return currentlyActive.get(thread);
	}
	
	public void count(String name, int time) {
		Countdown cd = countdowns.get(name);
		if(cd != null && !cd.isCounting()) {
			currentlyActive.put(Thread.currentThread(), cd);
			cd.count(time);
		}
	}
	
	public void finished(Countdown cd) {
		currentlyActive.remove(cd);
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
	
	public void setTime(String name, int time) {
		Countdown cd = countdowns.get(name);
		if(cd != null) cd.setTime(time);
	}
	
}
