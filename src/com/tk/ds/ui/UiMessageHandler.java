package com.tk.ds.ui;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import com.tk.ds.common.Constants;




public class UiMessageHandler implements Runnable {
	private String threadName;
	private DsUi snapshotUi;
	protected DatagramSocket serverSocket;

	public UiMessageHandler(DsUi snapshotUi) {
		
		this.snapshotUi = snapshotUi;
		
	}

	public void run() {
		try {
			DatagramSocket serverSocket = new DatagramSocket(Constants.HOST_PORT_UI_COM);
            byte[] receiveData = new byte[1024];
            while(true)
               {
                  DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                  serverSocket.receive(receivePacket);
                  String sentence = new String( receivePacket.getData());
                  snapshotUi.updateInfo(sentence);
              
                  
                 
               }
		} catch (Exception e) {

		}

	}

}