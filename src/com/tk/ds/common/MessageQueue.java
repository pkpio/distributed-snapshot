package com.tk.ds.common;

import java.util.LinkedList;

/**
 * Message queue for sending out messages. Sender will read from the queue and send the messages out.
 */
public class MessageQueue {
	public static LinkedList<Message> FIFO = new LinkedList<Message>();

	// Add message to the end of queue
	public void add(Message message) {
		FIFO.add(message);
	}

	// Remove message from head
	public Message remove() {
		if (FIFO.isEmpty()) {
			System.out.println("UNDERFLOW");
			return null;
		} else {
			Message message = FIFO.remove();
			return message;
		}
	}

	// Check if queue has elements
	public boolean hasElements() {
		return !FIFO.isEmpty();
	}

}
