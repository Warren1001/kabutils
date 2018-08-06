package com.kabryxis.kabutils.data;

public class MathHelp {
	
	public static int floor(double d) {
		return (int)d;
	}
	
	public static int ceil(double d) {
		return (int)Math.ceil(d);
	}
	
	public static double roundToHalf(double d) {
		return Math.round(d * 2) / 2.0;
	}
	
}
