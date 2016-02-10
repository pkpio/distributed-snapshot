package com.tk.ds.ui;

public class DsApp {
	DsUi snapshotUi;

	void init() {
		UiMessageHandler manager=new  UiMessageHandler();
		snapshotUi= new DsUi(manager);
		
		Thread S = new Thread(manager);
		S.start();
		snapshotUi.initUi();
	}

	// Start UI
	public static void main(String args[]) {
		new DsApp().init();
	}

}


