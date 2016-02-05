package com.tk.ds.ui;

import com.tk.ds.common.Snapshot;

public class DsApp {

	Snapshot snapshot;

	void init() {
		DsUi snapshotUi = new DsUi();
		snapshot = new Snapshot(snapshotUi);
		snapshotUi.initUi(snapshot);
	}

	// Start UI
	public static void main(String args[]) {
		new DsApp().init();

	}

}
