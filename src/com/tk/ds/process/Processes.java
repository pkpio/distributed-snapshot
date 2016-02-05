package com.tk.ds.process;

public class Processes {
	int processId;
	double accounBalance;
	
	 public int getProcessId() {
		return processId;
	}
	public void setProcessId(int processId) {
		this.processId = processId;
	}
	public double getAccounBalance() {
		return accounBalance;
	}
	public void setAccounBalance(double accounBalance) {
		this.accounBalance = accounBalance;
	}
	
	// Processes can send marker
	void sendMarker(){
		
	}
	
	// upon receiving message from watcher, update local balance
	void updateBalance(double amount){
		accounBalance+=amount;
	}
	
	// After random delay , transfer an amount to one of the 2 other processes 
	void transferAmoint(){
		
	}
	
	public static void main(String args[]){
		// It should have main function because it will be invoked as new process
		
	}

}
