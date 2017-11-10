package com.kabryxis.kabutils.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

import com.kabryxis.kabutils.cache.Cache;
import com.kabryxis.kabutils.concurrent.thread.AutoPausableThread;

public class Data {
	
	private static IOThread thread;
	
	private static void setup() {
		if(thread == null) (thread = new IOThread()).start();
	}
	
	public static void write(Path path, byte[] data) {
		setup();
		thread.write(path, data);
	}
	
	public static void read(Path path, Consumer<byte[]> future) {
		setup();
		thread.read(path, future);
	}
	
	public static void queue(Runnable runnable) {
		setup();
		thread.queue(runnable);
	}
	
	private static class IOThread extends AutoPausableThread {
		
		private final Queue<Runnable> actions = new ConcurrentLinkedQueue<>();
		
		public IOThread() {
			super("Async - I/O Thread");
			Cache.allocateCache(WriteAction.class, new LinkedBlockingQueue<>(), WriteAction::new);
			Cache.allocateCache(ReadAction.class, new LinkedBlockingQueue<>(), ReadAction::new);
		}
		
		public void write(Path path, byte[] data) {
			WriteAction action = Cache.get(WriteAction.class);
			action.reuse(path, data);
			queue(action);
		}
		
		public void read(Path path, Consumer<byte[]> future) {
			ReadAction action = Cache.get(ReadAction.class);
			action.reuse(path, future);
			queue(action);
		}
		
		public void queue(Runnable runnable) {
			actions.add(runnable);
		}
		
		@Override
		protected boolean shouldPause() {
			return actions.isEmpty();
		}
		
		@Override
		public void onPause() {}
		
		@Override
		public void onUnpause() {}
		
		@Override
		protected void end() {
			tick();
		}
		
		@Override
		protected void tick() {
			actions.forEach(Runnable::run);
			actions.clear();
		}
		
		private class WriteAction implements Runnable {
			
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
				Cache.cache(this);
			}
			
		}
		
		private class ReadAction implements Runnable {
			
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
				Cache.cache(this);
			}
			
		}
		
		@Override
		protected void begin() {}
		
		@Override
		protected boolean canTick() {
			return true;
		}
		
	}
	
}
