package org.lsfn.console_pc.data_store.sinks;

public class SinkString implements ISinkString {

	private String data;
	
	public SinkString(String data) {
		this.data = data;
	}
	
	@Override
	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
