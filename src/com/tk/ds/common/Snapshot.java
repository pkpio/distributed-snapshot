package com.tk.ds.common;

import com.tk.ds.ui.DsUi;

public class Snapshot {

	DsUi snapshotUi;

	public Snapshot(DsUi snapshotUi) {
		this.snapshotUi = snapshotUi;
	}
	public void sendMarker(){
		// To send marker
	}
	void updateInfoPanel(String info) {
snapshotUi.updateInfo(info);
		
	}

}
