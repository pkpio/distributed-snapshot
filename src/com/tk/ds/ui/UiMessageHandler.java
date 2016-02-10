package com.tk.ds.ui;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import com.tk.ds.common.Constants;
import com.tk.ds.common.MessageGUI;

public class UiMessageHandler implements Runnable {
	private String threadName;

	protected DatagramSocket serverSocket;
	UiUpdatesListner updateListener;

	public void addListners(UiUpdatesListner listner) {
		this.updateListener = listner;
	}

	public UiMessageHandler() {

	}

	public void run() {
		try {
			DatagramSocket serverSocket = new DatagramSocket(Constants.PORT_LISTEN_GUI);
			byte[] receiveData = new byte[1024];
			
			// Always keep listening
			while (true) {
				
				// Read datagram from socket
				MessageGUI msg = null;
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);	
				serverSocket.receive(receivePacket);
				byte[] data = receivePacket.getData();
				
				// Put data in an object stream and read the object
				ByteArrayInputStream in = new ByteArrayInputStream(data);
				ObjectInputStream is = new ObjectInputStream(in);
				try {
					msg = (MessageGUI) is.readObject();

					// Throw changes on to UI
					if (updateListener != null) {
						// Log message
						updateListener.updateInfo(msg.getLogMessage());

						// Method to change account balance
						updateListener.updateAccountBalance(msg.getBalance(), msg.getSender());
					}

				} catch (ClassCastException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
		}
		serverSocket.close();
	}

}