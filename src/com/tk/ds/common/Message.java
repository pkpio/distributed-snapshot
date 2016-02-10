package com.tk.ds.common;

import java.io.Serializable;

public class Message implements Serializable{
	private static final long serialVersionUID = 3463810144094354773L;
	
	int sender; 	// Sending process id
	int receiver;		// Receiving process id
	int amount;			// Amount being sent in this message
	
	public Message(){
		
	}
	
	/**
	 * Creates new message
	 * @param sender
	 * @param receiver
	 * @param amount
	 */
	public Message(int sender, int receiver, int amount) {
		this.sender = sender;
		this.receiver = receiver;
		this.amount = amount;
	}

	public int getSender() {
		return sender;
	}

	public void setSender(int sender) {
		this.sender = sender;
	}

	public int getReceiver() {
		return receiver;
	}

	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
