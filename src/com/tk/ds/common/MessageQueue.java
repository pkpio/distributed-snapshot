package com.tk.ds.common;

import java.util.LinkedList;

/**
 * Message queue for sending out messages. Sender will read from the queue and send the messages out.
 */
public class MessageQueue {
	public static LinkedList<MessageMoney> FIFO = new LinkedList<MessageMoney>();

	// Add message to the end of queue
	public void add(MessageMoney message) {
		FIFO.add(message);
	}

	// Remove message from head
	public MessageMoney remove() {
		if (FIFO.isEmpty()) {
			System.out.println("UNDERFLOW");
			return null;
		} else {
			MessageMoney message = FIFO.remove();
			return message;
		}
	}

	// Check if queue has elements
	public boolean hasElements() {
		return !FIFO.isEmpty();
	}

}
