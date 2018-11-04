package com.kabryxis.kabutils.data;

public class NumberConversions {
	
	public static int floor(double num) {
		int floor = (int)num;
		return (double)floor == num ? floor : floor - (int)(Double.doubleToRawLongBits(num) >>> 63);
	}
	
	public static int ceil(double num) {
		int floor = (int)num;
		return (double)floor == num ? floor : floor + (int)(~Double.doubleToRawLongBits(num) >>> 63);
	}
	
	public static int round(double num) {
		return floor(num + 0.5D);
	}
	
	public static double roundToHalf(double num) {
		return floor(num * 2) / 2.0D;
	}
	
	public static float roundToHalf(float num) {
		return floor(num * 2) / 2.0F;
	}
	
	public static double square(double num) {
		return num * num;
	}
	
	public static int toInt(Object obj, int def) {
		if(obj instanceof Number) return ((Number)obj).intValue();
		else if(obj != null) {
			try {
				return Integer.valueOf(obj.toString());
			} catch(NumberFormatException ignore) {}
		}
		return def;
	}
	
	public static int toInt(Object obj) {
		return toInt(obj, 0);
	}
	
	public static float toFloat(Object obj, float def) {
		if(obj instanceof Number) return ((Number)obj).floatValue();
		else if(obj != null) {
			try {
				return Float.valueOf(obj.toString());
			} catch(NumberFormatException ignore) {}
		}
		return def;
	}
	
	public static float toFloat(Object obj) {
		return toFloat(obj, 0.0F);
	}
	
	public static double toDouble(Object obj, double def) {
		if(obj instanceof Number) return ((Number)obj).doubleValue();
		else if(obj != null) {
			try {
				return Double.valueOf(obj.toString());
			} catch(NumberFormatException ignore) {}
		}
		return def;
	}
	
	public static double toDouble(Object obj) {
		return toDouble(obj, 0.0D);
	}
	
	public static long toLong(Object obj, long def) {
		if(obj instanceof Number) return ((Number)obj).longValue();
		else if(obj != null) {
			try {
				return Long.valueOf(obj.toString());
			} catch(NumberFormatException ignore) {}
		}
		return def;
	}
	
	public static long toLong(Object obj) {
		return toLong(obj, 0L);
	}
	
	public static short toShort(Object obj, short def) {
		if(obj instanceof Number) return ((Number)obj).shortValue();
		else if(obj != null) {
			try {
				return Short.valueOf(obj.toString());
			} catch(NumberFormatException ignore) {}
		}
		return def;
	}
	
	public static short toShort(Object obj) {
		return toShort(obj, (short)0);
	}
	
	public static byte toByte(Object obj, byte def) {
		if(obj instanceof Number) return ((Number)obj).byteValue();
		else if(obj != null) {
			try {
				return Byte.valueOf(obj.toString());
			} catch(NumberFormatException ignore) {}
		}
		return def;
	}
	
	public static byte toByte(Object obj) {
		return toByte(obj, (byte)0);
	}
	
}
