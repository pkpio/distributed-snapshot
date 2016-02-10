package com.tk.ds.common;

import java.util.LinkedList;

/**
 * Message queue used by SendMsg class
 * 
 * @author Ram
 *
 */
public class MessageQueue {
	public static LinkedList<Message> FIFO = new LinkedList<Message>();

	// add message
	public void add(Message message) {

		FIFO.add(message);

	}

	//Remove message from head
	public Message remove() {
		if (FIFO.isEmpty()) {
			System.out.println("UNDERFLOW");
			return null;
		} else {
			Message message = FIFO.remove();
			return message;
		}

	}

	public boolean hasElements() {
		// TODO Auto-generated method stub
		return !FIFO.isEmpty();
	}

}
