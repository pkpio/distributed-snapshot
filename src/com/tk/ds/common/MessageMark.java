package com.tk.ds.common;

public class MessageMark extends Message {
	private static final long serialVersionUID = -3276768409317628460L;

	public MessageMark(int sender, int receiver) {
		super.messageType = Type.MARK;
		super.sender = sender;
		super.receiver = receiver;
	}
	
}
