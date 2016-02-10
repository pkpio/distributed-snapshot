package com.tk.ds.common;


public class Constants {
	/**
	 * Thread time out period
	 */
	public static final long THREAD_TIME_OUT =5000;


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
