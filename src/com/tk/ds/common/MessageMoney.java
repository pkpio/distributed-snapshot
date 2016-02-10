package com.tk.ds.common;

public class MessageMoney extends Message {
	private static final long serialVersionUID = 3463810144094354773L;

	int amount; // Amount being sent in this message

	/**
	 * Creates new message
	 * 
	 * @param sender
	 * @param receiver
	 * @param amount
	 */
	public MessageMoney(int sender, int receiver, int amount) {
		super.messageType = Type.MONEY;
		super.sender = sender;
		super.receiver = receiver;
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
