package com.tk.ds.process;

import java.io.IOException;

public class ProcessHandler {
	
	public static void main(String[] args) {
		ProcessBuilder pb;
		Process process;
		// multiple commands
		for (int i = 0; i < 3; i++) {
			pb = new ProcessBuilder("java", "com.tk.ds.process.Processes");
			try {
				process = pb.start();
				IOThreadHandler outputHandler = new IOThreadHandler();
				outputHandler.start();
				process.waitFor();
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		System.out.println("startted");

	}

	private static class IOThreadHandler extends Thread {
		// Thread for reading FIFO

		public void run() {

		}

	}

}