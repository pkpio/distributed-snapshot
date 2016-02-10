package com.tk.ds.common;

public class MessageGUI extends Message {
	private static final long serialVersionUID = -6361388952075276923L;
	
	int process; // Process sending the message
	int balance; // Account balance of the process
	String logMessage; // Message to be logged in the list

	public MessageGUI() {
		super.messageType = Type.GUI;
	}

	public MessageGUI(int process, int balance, String logMessage) {
		super.messageType = Type.GUI;
		this.process = process;
		this.balance = balance;
		this.logMessage = logMessage;
	}

	public int getProcess() {
		return process;
	}

	public void setProcess(int process) {
		this.process = process;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public String getLogMessage() {
		return logMessage;
	}

	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
	}

}
