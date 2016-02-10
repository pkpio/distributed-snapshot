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
		UiMessageHandler manager=new  UiMessageHandler();
		snapshotUi= new DsUi(manager);
		Thread S = new Thread(manager);
		S.start();
		snapshotUi.initUi();
	
	}

	// Start UI
	public static void main(String args[]) {
		new DsApp().init();

	}

}


