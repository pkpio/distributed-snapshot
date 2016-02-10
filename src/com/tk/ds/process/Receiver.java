package com.tk.ds.process;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;

import com.tk.ds.common.MessageGUI;
import com.tk.ds.common.MessageMoney;

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
				MessageMoney msg = null;
				DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
				process.getServerSocket().receive(packet);
				byte[] data = packet.getData();
				ByteArrayInputStream in = new ByteArrayInputStream(data);
				ObjectInputStream is = new ObjectInputStream(in);
				
				try {
					msg = (MessageMoney) is.readObject();

					// Accept only messages addressed to us
					if (process.getProcessId() == msg.getReceiver()) {
						System.out.println(msg.getReceiver() + " <-- " + msg.getSender() + " : " + msg.getAmount());
						
						// update balance
						process.setAccountBalance(process.getAccountBalance() + msg.getAmount());
						
						// Notify event to GUI
						new Sender().sendToGUI(new MessageGUI(
								process.getProcessId(), 
								process.getAccountBalance(),
								"GOT  " + msg.getReceiver() + " <-- " + msg.getSender() + " : $" + msg.getAmount()));
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
