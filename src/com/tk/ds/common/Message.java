package com.tk.ds.common;

import java.io.Serializable;

public class Message implements Serializable {
	private static final long serialVersionUID = 2660663245208493382L;

	public enum Type {
		MONEY, MARK, GUI
	}

	Type messageType;	// Message type
	int sender; 		// Sending process id
	int receiver;		// Receiving process id

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

	public Type getMessageType() {
		return messageType;
	}

}
