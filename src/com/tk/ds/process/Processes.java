package com.tk.ds.process;

import java.util.concurrent.ThreadLocalRandom;


import com.tk.ds.common.Constants;
import com.tk.ds.common.MessageQueue;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

public class Processes {
	/**
	 * process ID of the current process
	 */
	protected int processId;
	/**
	 * amount available in the account
	 */
	int accounBalance;
	/**
	 * Queue to which Transaction messages are sent
	 */
	protected MessageQueue queue;

	public MessageQueue getQueue() {
		return queue;
	}

	public void setQueue(MessageQueue queue) {
		this.queue = queue;
	}

	protected DatagramSocket sendSocket;
	protected MulticastSocket receiveSocket;

	public DatagramSocket getClientSocket() {
		return sendSocket;
	}

	public void setClientSocket(DatagramSocket clientSocket) {
		this.sendSocket = clientSocket;
	}

	public DatagramSocket getServerSocket() {
		return receiveSocket;
	}

	public int getProcessId() {
		return processId;
	}

	public void setProcessId(int processId) {
		this.processId = processId;
	}

	public int getAccounBalance() {
		return accounBalance;
	}

	public void setAccounBalance(int accounBalance) {
		this.accounBalance = accounBalance;
	}

	// Processes can send marker
	void sendMarker() {

	}

	public void init(int processId) {

		queue = new MessageQueue();
		setProcessId(processId);
		setAccounBalance(ThreadLocalRandom.current().nextInt(10, 200000 + 1));

		try {
			sendSocket = new DatagramSocket();
			try {
				/**
				 * uses brodcasting , more than 1 client can listen to single port
				 */
				receiveSocket = new MulticastSocket(Constants.HOST_PORT);
				 InetAddress group =InetAddress.getByName(Constants.HOST_ADDR);
				// receiveSocket.joinGroup(group);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Initial balance " + getAccounBalance());
		Thread S = new Thread(new SendMsg("Sender", this));
		S.start();
		Thread R = new Thread(new RecvMsgs("Receiver", this));
		R.start();
		Thread T = new Thread(new TransferMoney("TxManager", this));
		T.start();

	}

	public static void main(String args[]) {
		// It should have main function because it will be invoked as new
		new Processes().init(Integer.parseInt(args[0]));

	}

}



