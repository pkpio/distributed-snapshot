package com.tk.ds.process;

import java.util.concurrent.ThreadLocalRandom;

import com.tk.ds.common.Constants;
import com.tk.ds.common.Message;

class TransferMoney extends Processes implements Runnable {
	private String threadName;
	Processes processes;

	public TransferMoney(String name, Processes processes) {
		threadName = name;
		this.processes = processes;

	}

	public void run() {
		while (true) {
			for (int i = 1; i < 4; i++) {
				if (processes.getProcessId() != i && processes.getAccounBalance() > 0)
					try {
						/*
						 * Prepare message and set marker and Sendmarker fields false
						 */
						Message transactionMessage = new Message();
						transactionMessage.setProcessId(i);
						transactionMessage.setMarker(false);
						transactionMessage.setSenderProcess(processes.getProcessId());
						transactionMessage.setSendMarkerRequest(false); // only packet sent
															// by
															// Gui
															// will have this
															// set
						int amountTobeTransferred = ThreadLocalRandom.current().nextInt(1, 300 + 1);
						if (amountTobeTransferred > processes.getAccounBalance())
							amountTobeTransferred = processes.getAccounBalance();
						transactionMessage.setAmount(amountTobeTransferred);
						synchronized (transactionMessage) {
							processes.setAccounBalance(processes.getAccounBalance() - transactionMessage.getAmount());
						}
						
						processes.getQueue().add(transactionMessage);
						new SendMsg().sendMessageToUi("[DEBIT ] From "+processes.getProcessId() + " To " + transactionMessage.getProcessId() + " ,Amount : $"
								+ transactionMessage.getAmount() );
						Thread.sleep(Constants.THREAD_TIME_OUT);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
	}

}
