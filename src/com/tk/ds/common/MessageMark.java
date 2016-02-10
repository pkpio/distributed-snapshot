package com.tk.ds.common;

public class MessageMark extends Message {
	private static final long serialVersionUID = -3276768409317628460L;

	int sender;
	int receiver;

	public MessageMark() {
		super.messageType = Type.MARK;
	}

	public MessageMark(int sender, int receiver) {
		super.messageType = Type.MARK;
		this.sender = sender;
		this.receiver = receiver;
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

}
