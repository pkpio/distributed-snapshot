package com.tk.ds.process;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;

import com.tk.ds.common.Constants;
import com.tk.ds.common.Message;

class Receiver extends Process implements Runnable {
	private String threadName;
	Process processes;

	public Receiver(String name, Process processes) {
		this.processes = processes;
		threadName = name;
		System.out.println("Creating " + threadName);
	}

	public void run() {

		while (true) {
			// recv msg
			try {

				byte[] recvBuf = new byte[5000];
				Message msg = null;
				DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
				processes.getServerSocket().receive(packet);
				byte[] data = packet.getData();
				ByteArrayInputStream in = new ByteArrayInputStream(data);
				ObjectInputStream is = new ObjectInputStream(in);
				try {

					msg = (Message) is.readObject();
					// check if its is marker
					if(msg.isMarker()){
						handleMarker(msg);
					}
					// if not marker check if it is for this process
					if (processes.getProcessId() == msg.getProcessId()) {
						// update balance
						processes.setAccounBalance(processes.getAccounBalance() + msg.getAmount());
						// send msg to gui
						new Sender().sendMessageToUi("[CREDIT] From " + msg.getSenderProcess() + " To "
								+ processes.getProcessId() + ", Amount : $" + msg.getAmount());
					}

				} catch (ClassNotFoundException e) {

					e.printStackTrace();
				}

			} catch (Exception e) {

			}
			// check if its sendMraker request and for current process
			
			
			
			

		}
	}

	private void handleMarker(Message msg) {
		// TODO Auto-generated method stub
		
	}

}
