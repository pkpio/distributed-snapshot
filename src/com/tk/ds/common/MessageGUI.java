package com.tk.ds.common;

public class MessageGUI extends Message {
	private static final long serialVersionUID = -6361388952075276923L;
	
	int balance; // Account balance of the process
	String logMessage; // Message to be logged in the list

	public MessageGUI(int sender, int balance, String logMessage) {
		super.messageType = Type.GUI;
		super.sender = sender;
		super.receiver = 0;
		this.balance = balance;
		this.logMessage = logMessage;
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
