package com.kabryxis.kabutils.string;

public class IncrementalString {
	
	private final String string;
	private final int startingNumber;
	
	private int number;
	
	public IncrementalString(String string, int startingNumber) {
		this.string = string;
		this.startingNumber = startingNumber;
		this.number = startingNumber;
	}
	
	public IncrementalString(String string) {
		this(string, 0);
	}
	
	public String increment() {
		number++;
		return get();
	}
	
	public String get() {
		return string.replace("[x]", String.valueOf(number));
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public String reset() {
		this.number = startingNumber;
		return get();
	}
	
}
