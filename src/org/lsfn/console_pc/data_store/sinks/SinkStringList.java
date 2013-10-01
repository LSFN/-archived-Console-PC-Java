package org.lsfn.console_pc.data_store.sinks;

import java.util.ArrayList;
import java.util.List;

public class SinkStringList implements ISinkStringList {
	
	private List<String> data;
	
	public SinkStringList() {
		this.data = new ArrayList();
	}
	
	public void setData(List<String> data) {
		this.data = data;
	}
	
	@Override
	public List<String> getData() {
		return this.data;
	}

}
