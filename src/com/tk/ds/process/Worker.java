package com.tk.ds.process;

import java.util.concurrent.ThreadLocalRandom;

import com.tk.ds.common.Message;
import com.tk.ds.common.Util;

class Worker extends Process implements Runnable {
	private String threadName;
	Process processes;

	public Worker(String name, Process processes) {
		threadName = name;
		this.processes = processes;

	}

	/**
	 * Forever randomly sends out money from account to other processes.
	 */
	public void run() {
		while (true) {
			// Sleep for a random time
			Util.sleepThreadRandom();
			
			for (int i = 1; i < 4; i++) {
				if (processes.getProcessId() != i && processes.getAccountBalance() > 0)
					try {
						
						// Decide on the amount and prepare a packet to be sent
						int amountToSend = ThreadLocalRandom.current().nextInt(1, 300 + 1);
						if (amountToSend > processes.getAccountBalance())
							amountToSend = processes.getAccountBalance();
						Message msg = new Message(processes.getProcessId(), i, amountToSend);
						
						// Update the balance of this process
						processes.setAccountBalance(processes.getAccountBalance() - msg.getAmount());
						
						// Notify the GUI about this event
						new Sender().sendMessageToUi("[DEBIT ]"
								+ " From " + msg.getSender() 
								+ " To " + msg.getReceiver()
								+ " Amount : $" + msg.getAmount());
						
						// Add message to the queue for sending out
						processes.getQueue().add(msg);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		}
		// End while
	}
	

}
