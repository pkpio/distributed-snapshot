package com.tk.ds.common;

import java.io.Serializable;

public class Message implements Serializable {
	private static final long serialVersionUID = 2660663245208493382L;
	
	public enum Type {
			MONEY,
			MARK,
			GUI
	}
	Type messageType;
	
	public Type getMessageType(){
		return messageType;
	}
}
