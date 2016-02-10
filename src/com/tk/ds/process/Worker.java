package com.tk.ds.process;

import java.util.concurrent.ThreadLocalRandom;

import com.tk.ds.common.MessageGUI;
import com.tk.ds.common.MessageMoney;
import com.tk.ds.common.Util;

class Worker extends Process implements Runnable {
	private String threadName;
	Process process;

	public Worker(String name, Process processes) {
		threadName = name;
		this.process = processes;

	}

	/**
	 * Forever randomly sends out money from account to other processes.
	 */
	public void run() {
		while (true) {
			
			for (int i = 1; i < 4; i++) {
				if (process.getProcessId() != i && process.getAccountBalance() > 0)
					try {
						// Sleep for a random time
						Util.sleepThreadRandom();
						
						// Decide on the amount and prepare a packet to be sent
						int amountToSend = ThreadLocalRandom.current().nextInt(1, 300 + 1);
						if (amountToSend > process.getAccountBalance())
							amountToSend = process.getAccountBalance();
						MessageMoney msg = new MessageMoney(process.getProcessId(), i, amountToSend);
						
						// Update the balance of this process
						process.setAccountBalance(process.getAccountBalance() - msg.getAmount());
						
						// Notify event to GUI
						Sender.sendToGUI(new MessageGUI(
								process.getProcessId(), 
								process.getAccountBalance(),
								"SENT " + msg.getSender() + " --> " + msg.getReceiver() + " : $" + msg.getAmount()));
						
						// Add message to the queue for sending out
						System.out.println(msg.getSender() + " --> " + msg.getReceiver() + " : " + msg.getAmount());
						process.getQueue().add(msg);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		}
		// End while
	}
	

}
