package com.ice.cloud.utils;

public class Time {
	public static String fromMStoHMS(long time) {
		int seconds = (int) (time / 1000) % 60;
		int minutes = (int) ((time / (1000*60)) % 60);
		int hours = (int) ((time / (1000*60*60)) % 24);
		return (hours == 0 ? "" : String.format("%02d", hours)+":")+(minutes == 0 ? "" : String.format("%02d", minutes)+":")+String.format("%02d", seconds);
	}
}
