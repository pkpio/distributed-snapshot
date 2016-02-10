package com.tk.ds.common;

import java.util.concurrent.ThreadLocalRandom;

public class Util {
	
	/**
	 * Get a random delay between Min and Max in constants
	 * @return
	 */
	public static long getRandomDelay(){
		return ThreadLocalRandom.current().nextLong(Constants.SLEEP_MIN_TIME, Constants.SLEEP_MAX_TIME + 1);
	}
	
	/**
	 * Attempts to sleep the calling thread for given duration
	 * @param duration
	 */
	public static void sleepThread(long duration){
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Attempts to sleep the calling thread for a random time 
	 * between min and max sepecified in constants
	 * @param duration
	 */
	public static void sleepThreadRandom(){
		try {
			Thread.sleep(getRandomDelay());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
