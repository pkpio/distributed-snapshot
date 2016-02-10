package com.tk.ds.ui;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import com.tk.ds.common.Constants;




public class UiMessageHandler implements Runnable {
	private String threadName;
	
	protected DatagramSocket serverSocket;
	UiUpdatesListner updateListener;
	
	public void addListners(UiUpdatesListner listner){
		this.updateListener=listner;
	}
	public UiMessageHandler() {
		
		
	}

	public void run() {
		try {
			DatagramSocket serverSocket = new DatagramSocket(Constants.PORT_LISTEN_GUI);
            byte[] receiveData = new byte[1024];
            while(true)
               {
                  DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                  serverSocket.receive(receivePacket);
                  String sentence = new String( receivePacket.getData());
                  if(updateListener!=null)
                	  updateListener.updateInfo(sentence);
                  //Method to change account balance

                  updateListener.updateAccountBalance(100+"", 2);
              
                  
                 
               }
		} catch (Exception e) {

		}

	}

}