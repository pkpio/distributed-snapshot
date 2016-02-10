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
	 */
	public void run() {

		while (true) {
			try {
				byte[] recvBuf = new byte[5000];
				Message msg = null;
				DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
				process.getServerSocket().receive(packet);
				byte[] data = packet.getData();
				ByteArrayInputStream in = new ByteArrayInputStream(data);
				ObjectInputStream is = new ObjectInputStream(in);
				try {
					msg = (Message) is.readObject();

					// if not marker check if it is for this process
					if (process.getProcessId() == msg.getProcessId()) {
						// update balance
						process.setAccounBalance(process.getAccounBalance() + msg.getAmount());
						// send msg to gui
						new Sender().sendMessageToUi("[CREDIT] From " + msg.getSenderProcess() + " To "
								+ process.getProcessId() + ", Amount : $" + msg.getAmount());
					}

				} catch (ClassNotFoundException e) {

					e.printStackTrace();
				}

			} catch (Exception e) {

			}
			// check if its sendMraker request and for current process
			
			
			
			

		}
	}

}
