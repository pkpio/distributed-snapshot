package com.tk.ds.process;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.tk.ds.common.Constants;
import com.tk.ds.common.Message;

public class SendMsg extends Processes implements Runnable {
	private String threadName;
	Processes processes;

	public SendMsg(String name, Processes processes) {
		threadName = name;
		this.processes = processes;
	}

	public SendMsg() {
		// TODO Auto-generated constructor stub
	}

	void sendMessageToUi(String msg) {
		try {
			sendSocket = new DatagramSocket();
			InetAddress IPAddress = InetAddress.getByName(Constants.HOST_ADDR);
			byte[] sendData = new byte[1024];
			sendData = msg.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress,
					Constants.HOST_PORT_UI_COM);
			sendSocket.send(sendPacket);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void run() {

		while (true) {
			if (processes.getQueue().hasElements()) {
				//pop msg from queue
				Message message=processes.getQueue().remove();
				
				//broadcast
				broadCastMessage(message);
			}
			try {
				Thread.sleep(Constants.THREAD_TIME_OUT);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	public void broadCastMessage(Message message){
		try {
			sendSocket = new DatagramSocket();
			InetAddress IPAddress = InetAddress.getByName(Constants.HOST_ADDR);
			Thread.sleep(Constants.THREAD_TIME_OUT);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(outputStream);
			os.writeObject(message);
			byte[] sendBuf = outputStream.toByteArray();
			DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, IPAddress,
					Constants.HOST_PORT);
			sendSocket.send(sendPacket);
			os.close();

		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
