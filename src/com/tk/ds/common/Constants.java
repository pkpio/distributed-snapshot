package com.tk.ds.common;


public class Constants {
	/**
	 * Thread min. time out for sleep
	 */
	public static final long SLEEP_MIN_TIME = 1500;
	
	/**
	 * Thread min. time out for sleep
	 */
	public static final long SLEEP_MAX_TIME = 6000;


	/**
	 * Network IP address
	 */
	public static String HOST_ADDR = "127.0.0.1";
	
	
	/**
	 * Network port for receiving UDP packets. The corresponding process 
	 * would be hearing for new packets on these ports
	 */
	public static int PORT_LISTEN_GUI = 9876;
	public static int PORT_LISTEN_PROCESS1 = 9877;
	public static int PORT_LISTEN_PROCESS2 = 9878;
	public static int PORT_LISTEN_PROCESS3 = 9879;
	
}
