package com.tk.ds.process;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.tk.ds.common.Constants;
import com.tk.ds.common.Message;
import com.tk.ds.common.MessageGUI;
import com.tk.ds.common.Util;

public class Sender extends Process implements Runnable {
	private String threadName;
	Process processes;

	public Sender(String name, Process processes) {
		threadName = name;
		this.processes = processes;
	}

	public Sender() {
	}

	/**
	 * Continuously check for message at head of the queue and send it to the
	 * corresponding process
	 */
	public void run() {
		// Send current balance to GUI
		Util.sleepThread(1000);
		sendToGUI(new MessageGUI(processes.getProcessId(), processes.getAccountBalance(),
				"INIT balance P" + processes.getProcessId() + " : $" + processes.getAccountBalance()));

		while (true) {
			// Sleep for random time enough to emulate communication delays
			Util.sleepThreadCommunication();

			// Send element at the head of the queue
			if (processes.getQueue().hasElements()) {
				Message message = processes.getQueue().remove();
				sendToProcess(message);
			}
		}
	}

	/**
	 * Send message to another process. Process address will be read from the
	 * message
	 * 
	 * @param message
	 */
	public static void sendToProcess(Message message) {
		sendObject(message, Constants.PORT_LISTEN_GUI + message.getReceiver());
	}

	/**
	 * Send message to GUI.
	 * 
	 * @param msg
	 */
	public static void sendToGUI(MessageGUI message) {
		sendObject(message, Constants.PORT_LISTEN_GUI);
	}

	/**
	 * Send object to a process
	 * 
	 * @param object
	 * @param receiver
	 */
	static void sendObject(Object object, int receiver) {
		try {
			// Close the previous socket if left open
			if (sendSocket != null && !sendSocket.isClosed())
				sendSocket.close();

			// Build a socket
			sendSocket = new DatagramSocket();
			InetAddress IPAddress = InetAddress.getByName(Constants.HOST_ADDR);

			// Put message object in a stream
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(outputStream);
			os.writeObject(object);

			// Send stream over the socket
			byte[] sendBuf = outputStream.toByteArray();
			DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, IPAddress, receiver);
			sendSocket.send(sendPacket);
			os.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
