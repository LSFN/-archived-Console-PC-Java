package org.lsfn.console_pc.data_store.sinks;

public class SinkBoolean implements ISinkBoolean {

	Boolean data;
	
	public SinkBoolean(boolean state) {
		this.data = state;
	}
	
	public void setData(boolean state) {
		this.data = state;
	}
	
	@Override
	public Boolean getData() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
