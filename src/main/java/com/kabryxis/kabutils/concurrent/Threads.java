package com.kabryxis.kabutils.concurrent;

public class Threads {
	
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void wait(Object obj) {
		try {
			obj.wait();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
