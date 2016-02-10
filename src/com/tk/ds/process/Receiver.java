package com.tk.ds.process;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;

import com.tk.ds.common.Message;
import com.tk.ds.common.MessageGUI;
import com.tk.ds.common.MessageMark;
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
				DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
				process.getServerSocket().receive(packet);
				byte[] data = packet.getData();
				
				// Read object in the stream
				ByteArrayInputStream in = new ByteArrayInputStream(data);
				ObjectInputStream is = new ObjectInputStream(in);
				Object rObj =  is.readObject();
				
				// Process message based on it's type
				try {
					Message msg = (Message)rObj;

					if(msg.getMessageType() == Message.Type.MONEY)
						processMoney((MessageMoney) rObj);
					else if(msg.getMessageType() == Message.Type.MARK)
						processMark((MessageMark) rObj);
					
				} catch (ClassCastException e) {
					System.out.println("Unknown object type received!");
				}

			} catch (Exception e) {

			}
		}
		// End while
	}
	
	/**
	 * Processes a normal money packet
	 */
	void processMoney(MessageMoney msg){
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
	}
	
	/**
	 * Processes a mark packet
	 */
	void processMark(MessageMark msg){
		// -TODO
	}
	
	

}
