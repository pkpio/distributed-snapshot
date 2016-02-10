package com.tk.ds.ui;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.tk.ds.common.Constants;
import com.tk.ds.common.Message;

import com.tk.ds.process.Processes;

public class DsApp {

	
	DsUi snapshotUi;

	void init() {
		snapshotUi= new DsUi();
		snapshotUi.initUi();
		initProcess();
		Thread S = new Thread(new UiMessageHandler( snapshotUi));
		S.start();
	}

	@SuppressWarnings("unused")
	void initProcess() {
		ProcessBuilder pb;
		Process process;
		String javaHome = System.getProperty("java.home");
		String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
		String classpath = System.getProperty("java.class.path");
		String className = Processes.class.getCanonicalName();

		ProcessBuilder builder;
		for (int i = 1; i < 4; i++) {
			try {
				builder = new ProcessBuilder(javaBin, "-cp", classpath, className,i+"");
				process = builder.start();
				System.out.println("started process ");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	// Start UI
	public static void main(String args[]) {
		new DsApp().init();

	}

}


