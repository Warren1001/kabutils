package com.kabryxis.kabutils;

import java.io.InputStream;
import java.util.Scanner;

import com.kabryxis.kabutils.concurrent.Threads;
import com.kabryxis.kabutils.concurrent.thread.QuittableThread;

public class Console extends QuittableThread {
	
	private final MessageHandler handler;
	private final Scanner scanner;
	
	public Console(MessageHandler handler) {
		this(handler, System.in);
	}
	
	public Console(MessageHandler handler, InputStream input) {
		super("Async - System Input");
		this.handler = handler;
		this.scanner = new Scanner(input);
	}
	
	@Override
	public void run() {
		while(isRunning()) {
			String line;
			while((line = scanner.nextLine()) != null) {
				handler.handle(line);
			}
			Threads.sleep(50);
		}
		scanner.close();
	}
	
}
