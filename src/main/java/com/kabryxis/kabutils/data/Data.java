package com.kabryxis.kabutils.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

import com.kabryxis.kabutils.cache.Cache;
import com.kabryxis.kabutils.concurrent.thread.ActionThread;

public class Data {
	
	private static ActionThread thread;
	
	private static void setup() {
		if(thread == null) {
			Cache.allocateCache(WriteAction.class, new LinkedBlockingQueue<>(), WriteAction::new);
			Cache.allocateCache(ReadAction.class, new LinkedBlockingQueue<>(), ReadAction::new);
			(thread = new ActionThread("Async - I/O Thread", new ConcurrentLinkedQueue<>())).start();
		}
	}
	
	public static void write(Path path, byte[] data) {
		WriteAction action = Cache.get(WriteAction.class);
		action.reuse(path, data);
		queue(action);
	}
	
	public static void read(Path path, Consumer<byte[]> future) {
		ReadAction action = Cache.get(ReadAction.class);
		action.reuse(path, future);
		queue(action);
	}
	
	public static void queue(Runnable runnable) {
		setup();
		thread.queue(runnable);
	}
	
	private static class WriteAction implements Runnable {
		
		private Path path;
		private byte[] data;
		
		public void reuse(Path path, byte[] data) {
			this.path = path;
			this.data = data;
		}
		
		@Override
		public void run() {
			try {
				Files.write(path, data);
			}
			catch(IOException e) {
				e.printStackTrace();
			}
			path = null;
			data = null;
			Cache.cache(this);
		}
		
	}
	
	private static class ReadAction implements Runnable {
		
		private Path path;
		private Consumer<byte[]> future;
		
		public void reuse(Path path, Consumer<byte[]> future) {
			this.path = path;
			this.future = future;
		}
		
		@Override
		public void run() {
			try {
				future.accept(Files.readAllBytes(path));
			}
			catch(IOException e) {
				e.printStackTrace();
			}
			path = null;
			future = null;
			Cache.cache(this);
		}
		
	}
	
}
