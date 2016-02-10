package com.tk.ds.common;

import java.io.Serializable;

public class Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3463810144094354773L;
	
/**
 * Process which sends transaction will set this  
 */
	int senderProcess;
	/**
	 * Id of the process to which the amount has to be sent
	 */
	int processId;
	/**
	 * amount to credited
	 */
	int amount;
	
	/** Message sent be one of the process upon getting marker request 
	 * 
	 */
	boolean isMarker;
	
	/**
	 * Marker response , Amount will be amount available in the account at that time. sender account id will be process ID. 
	 */
	boolean sendMarkerRequest;
	
	public boolean isMarker() {
		return isMarker;
	}
	public void setMarker(boolean isMarker) {
		this.isMarker = isMarker;
	}
	public int getProcessId() {
		return processId;
	}
	public void setProcessId(int processId) {
		this.processId = processId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public boolean isSendMarkerRequest() {
		return sendMarkerRequest;
	}
	public void setSendMarkerRequest(boolean sendMarkerRequest) {
		this.sendMarkerRequest = sendMarkerRequest;
	}
	public int getSenderProcess() {
		return senderProcess;
	}
	public void setSenderProcess(int senderProcess) {
		this.senderProcess = senderProcess;
	}
}
