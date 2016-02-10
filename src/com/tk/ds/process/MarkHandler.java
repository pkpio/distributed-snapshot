package com.tk.ds.process;

import java.util.ArrayList;

import com.tk.ds.common.Message;
import com.tk.ds.common.MessageGUI;
import com.tk.ds.common.MessageMark;
import com.tk.ds.common.MessageMoney;

/**
 * Class that handles mark messages and snap-shotting
 */
public class MarkHandler {
	/**
	 * State monitoring
	 */
	static Boolean stateProcessRecorded = false;
	static Boolean stateChannel1Recording = false; // Incoming channel1
	static Boolean stateChannel2Recording = false; // Incoming channel2
	static Boolean stateChannel3Recording = false; // Incoming channel3

	// List of messages on channels since recording started
	static ArrayList<Message> stateChannel1 = new ArrayList<Message>();
	static ArrayList<Message> stateChannel2 = new ArrayList<Message>();
	static ArrayList<Message> stateChannel3 = new ArrayList<Message>();

	/**
	 * State information
	 */
	static int stateBalance = 0;

	public static synchronized void processMark(Process process, MessageMark msg) {

		// Process has not recorded state yet
		if (!stateProcessRecorded) {
			stateBalance = process.getAccountBalance();
			stateProcessRecorded = true;

			// Send markers over all other channels
			process.getQueue().add(new MessageMark(process.getProcessId(), 1));
			process.getQueue().add(new MessageMark(process.getProcessId(), 2));
			process.getQueue().add(new MessageMark(process.getProcessId(), 3));

			// set recording on channels
			stateChannel1Recording = true;
			stateChannel2Recording = true;
			stateChannel3Recording = true;
		}

		// Turn off recording for the channel over which message has arrived &
		// also for own channel (channel1 for process1)
		if (msg.getSender() == 1 || process.processId == 1)
			stateChannel1Recording = false;
		if (msg.getSender() == 2 || process.processId == 2)
			stateChannel2Recording = false;
		if (msg.getSender() == 3 || process.processId == 3)
			stateChannel3Recording = false;

		// If all the recordings are off => state record complete
		if (!stateChannel1Recording && !stateChannel2Recording && !stateChannel3Recording) {
			// Calculate amount of money in each incoming channel
			int c1 = 0;
			int c2 = 0;
			int c3 = 0;

			// Count money in channel 1
			for (int i = 0; i < stateChannel1.size(); i++) {
				Message genMsg = stateChannel1.get(i);

				// Only take money messages
				if (genMsg.getMessageType() == Message.Type.MONEY)
					c1 += ((MessageMoney) genMsg).getAmount();
			}

			// Count money in channel 2
			for (int i = 0; i < stateChannel2.size(); i++) {
				Message genMsg = stateChannel2.get(i);

				// Only take money messages
				if (genMsg.getMessageType() == Message.Type.MONEY)
					c2 += ((MessageMoney) genMsg).getAmount();
			}

			// Count money in channel 3
			for (int i = 0; i < stateChannel3.size(); i++) {
				Message genMsg = stateChannel3.get(i);

				// Only take money messages
				if (genMsg.getMessageType() == Message.Type.MONEY)
					c3 += ((MessageMoney) genMsg).getAmount();
			}

			// Log recorded state
			System.out.println("State recording complete : " + process.getProcessId() + " balance : " + stateBalance
					+ " c1 : " + c1 + " c2 : " + c2 + " c3 : " + c3);

			// Notify UI about this
			Sender.sendToGUI(
					new MessageGUI(process.processId, process.accountBalance, "Snapshot at P" + process.getProcessId()
							+ " - bal : $" + stateBalance + " c1 : $" + c1 + " c2 : $" + c2 + " c3 : $" + c3));
			
			// Reset state
			stateProcessRecorded = false;
			stateChannel1Recording = false; // Incoming channel1
			stateChannel2Recording = false; // Incoming channel2
			stateChannel3Recording = false; // Incoming channel3
			stateChannel1.clear();
			stateChannel2.clear();
			stateChannel3.clear();
		}

	}

	/**
	 * Processing a non-mark message
	 */
	public static synchronized void processMessage(Message msg) {
		// Record it if, it arrived over a recording enabled channel
		if (msg.getSender() == 1 && stateChannel1Recording)
			stateChannel1.add(msg);
		else if (msg.getSender() == 2 && stateChannel2Recording)
			stateChannel2.add(msg);
		else if (msg.getSender() == 3 && stateChannel3Recording)
			stateChannel3.add(msg);
	}

}
