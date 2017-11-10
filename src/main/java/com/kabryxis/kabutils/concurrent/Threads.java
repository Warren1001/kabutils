package com.kabryxis.kabutils.concurrent;

import java.util.HashSet;
import java.util.Set;

import com.kabryxis.kabutils.concurrent.thread.QuittableThread;

/**
 * A utility class with helpful methods for manipulating threads.
 * 
 * @author Kabryxis
 *
 */
public final class Threads {
	
	private static Set<QuittableThread> quittables;
	
	public static void registerQuittable(QuittableThread quittable) {
		if(quittables == null) quittables = new HashSet<>();
		quittables.add(quittable);
	}
	
	public static void stopThreads() {
		if(quittables != null) quittables.forEach(QuittableThread::quit);
	}
	
	public static Thread start(Runnable runnable) {
		Thread thread = new Thread(runnable);
		thread.start();
		return thread;
	}
	
	public static Thread start(Runnable runnable, String name) {
		Thread thread = new Thread(runnable, name);
		thread.start();
		return thread;
	}
	
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void wait(Object obj) {
		try {
			obj.wait();
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
