package com.tk.ds.common;

import java.util.LinkedList;

public class MessageQueue {
	// Static object shared among all processes and a thread, only thread can remove object
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

}
