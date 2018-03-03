package com.ice.cloud.utils;

public class Time {
	public static String fromMStoHMS(long time) {
		int seconds = (int) (time / 1000) % 60;
		int minutes = (int) ((time / (1000*60)) % 60);
		int hours = (int) ((time / (1000*60*60)) % 24);
		return "`["+(hours == 0 ? "" : String.format("%02d", hours)+"h ")+(minutes == 0 ? "" : String.format("%02d", minutes)+"m ")+String.format("%02d", seconds)+"s"+"]`";
	}
}
