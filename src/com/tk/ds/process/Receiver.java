package com.tk.ds.process;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;

import com.tk.ds.common.Message;

class Receiver extends Process implements Runnable {
	private String threadName;
	Process process;

	public Receiver(String name, Process processes) {
		this.process = processes;
		threadName = name;
		System.out.println("Starting receiver for : " + threadName);
	}

	
	/**
	 * Forever listens for new messages on the listen port of this process.
	 * -TODO- Handling Mark messages not done!
	 */
	public void run() {

		while (true) {
			try {
				// Read the packet from incoming socket connection
				byte[] recvBuf = new byte[5000];
				Message msg = null;
				DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
				process.getServerSocket().receive(packet);
				byte[] data = packet.getData();
				ByteArrayInputStream in = new ByteArrayInputStream(data);
				ObjectInputStream is = new ObjectInputStream(in);
				
				try {
					msg = (Message) is.readObject();

					// Accept only messages addressed to us
					if (process.getProcessId() == msg.getReceiver()) {
						System.out.println("Process " + process.processId 
								+ " received packet. From : " + msg.getSender() + " Amount : " + msg.getAmount());
						
						// Notify event to GUI
						new Sender().sendMessageToUi("[CREDIT] From " + msg.getSender() + " To "
								+ msg.getReceiver() + ", Amount : $" + msg.getAmount());
						
						// update balance
						process.setAccountBalance(process.getAccountBalance() + msg.getAmount());
					}

				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}

			} catch (Exception e) {

			}
		}
		// End while
	}

}
