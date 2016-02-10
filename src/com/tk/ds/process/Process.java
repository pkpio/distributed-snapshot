package com.tk.ds.process;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.ThreadLocalRandom;

import com.tk.ds.common.Constants;
import com.tk.ds.common.MessageQueue;

public class Process {
	/**
	 * process ID of the current process
	 */
	protected int processId;
	/**
	 * amount available in the account and it's lock
	 */
	int accountBalance;
	Object balanceLock = new Object();
	
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

	protected static DatagramSocket sendSocket;
	protected DatagramSocket receiveSocket;

	public DatagramSocket getClientSocket() {
		return sendSocket;
	}

	public void setClientSocket(DatagramSocket clientSocket) {
		sendSocket = clientSocket;
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

	public int getAccountBalance() {
		synchronized (balanceLock){
			return accountBalance;
		}
	}

	public void setAccountBalance(int accounBalance) {
		synchronized (balanceLock){
			this.accountBalance = accounBalance;
		}
	}

	// Processes can send marker
	void sendMarker() {

	}

	public void init(int processId) {
		queue = new MessageQueue();
		setProcessId(processId);
		setAccountBalance(ThreadLocalRandom.current().nextInt(10, 200000 + 1));

		try {
			sendSocket = new DatagramSocket();
			try {
				
				receiveSocket = new DatagramSocket(Constants.PORT_LISTEN_GUI + processId);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}

		System.out.println("Initial balance " + getAccountBalance());
		Thread S = new Thread(new Sender("Sender", this));
		S.start();
		Thread R = new Thread(new Receiver("Receiver", this));
		R.start();
		Thread T = new Thread(new Worker("TxManager", this));
		T.start();
	}

	public static void main(String args[]) {
		// It should have main function because it will be invoked as new
		new Process().init(Integer.parseInt(args[0]));

	}

}



