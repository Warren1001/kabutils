package com.kabryxis.kabutils.data;

import com.kabryxis.kabutils.concurrent.thread.ActionThread;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

@Deprecated
public class Data {
	
	private static ActionThread thread;
	
	private static void setup() {
		if(thread == null) (thread = new ActionThread("Async - I/O Thread", new ConcurrentLinkedQueue<>())).start();
	}
	
	public static void write(Path path, byte[] data) {
		queue(new WriteAction(path, data));
	}
	
	public static void read(Path path, Consumer<byte[]> future) {
		queue(new ReadAction(path, future));
	}
	
	public static void queue(Runnable runnable) {
		setup();
		thread.queue(runnable);
	}
	
	private static class WriteAction implements Runnable {
		
		private final Path path;
		private final byte[] data;
		
		public WriteAction(Path path, byte[] data) {
			this.path = path;
			this.data = data;
		}
		
		@Override
		public void run() {
			try {
				Files.write(path, data);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private static class ReadAction implements Runnable {
		
		private final Path path;
		private final Consumer<byte[]> future;
		
		public ReadAction(Path path, Consumer<byte[]> future) {
			this.path = path;
			this.future = future;
		}
		
		@Override
		public void run() {
			try {
				future.accept(Files.readAllBytes(path));
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
