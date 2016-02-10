package com.tk.ds.process;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ThreadLocalRandom;

import com.tk.ds.common.Constants;
import com.tk.ds.common.Message;

public class Sender extends Process implements Runnable {
	private String threadName;
	Process processes;

	public Sender(String name, Process processes) {
		threadName = name;
		this.processes = processes;
	}

	public Sender() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Send message to GUI. 
	 * @param msg
	 * @throws InterruptedException
	 */
	public void sendMessageToUi(String msg) {
		try {
			// Close the previous socket if left open
			if(sendSocket != null && !sendSocket.isClosed())
				sendSocket.close();
			
			sendSocket = new DatagramSocket();
			InetAddress IPAddress = InetAddress.getByName(Constants.HOST_ADDR);
			byte[] sendData = new byte[1024];
			sendData = msg.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress,
					Constants.PORT_LISTEN_GUI);
			sendSocket.send(sendPacket);
			// sendSocket.close();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Continuously check for message at head of the queue and send it
	 * to the corresponding process
	 */
	public void run() {
		while (true) {
			if (processes.getQueue().hasElements()) {
				// pop msg from queue
				Message message = processes.getQueue().remove();
				sendMessage(message);
			} else {
				try {
					Thread.sleep(ThreadLocalRandom.current().nextLong(10,100));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * Broadcast the message to all processes except the GUI. This essentially 
	 * sends out unicast messages to each process
	 *  
	 * @param message
	 */
	public void broadCastMessage(Message message) {
		try {
			// Close the previous socket if left open
			if(sendSocket != null && !sendSocket.isClosed())
				sendSocket.close();
			
			sendSocket = new DatagramSocket();
			InetAddress IPAddress = InetAddress.getByName(Constants.HOST_ADDR);
			// Thread.sleep(Constants.THREAD_TIME_OUT);
			for (int i = 1; i < 4; i++) {
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				ObjectOutputStream os = new ObjectOutputStream(outputStream);
				os.writeObject(message);
				byte[] sendBuf = outputStream.toByteArray();
				DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, IPAddress,
						Constants.PORT_LISTEN_GUI + i);
				sendSocket.send(sendPacket);
				os.close();
				//sendSocket.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Send message to another process. Process address will be read from the message
	 * @param message
	 */
	public void sendMessage(Message message) {
		try {
			// Close the previous socket if left open
			if(sendSocket != null && !sendSocket.isClosed())
				sendSocket.close();
			
			sendSocket = new DatagramSocket();
			InetAddress IPAddress = InetAddress.getByName(Constants.HOST_ADDR);
			// Thread.sleep(Constants.THREAD_TIME_OUT);
			for (int i = 1; i < 4; i++) {
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				ObjectOutputStream os = new ObjectOutputStream(outputStream);
				os.writeObject(message);
				byte[] sendBuf = outputStream.toByteArray();
				DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, IPAddress,
						Constants.PORT_LISTEN_GUI + message.getProcessId());
				sendSocket.send(sendPacket);
				os.close();
				// Thread.sleep(Constants.THREAD_TIME_OUT);
				// sendSocket.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
