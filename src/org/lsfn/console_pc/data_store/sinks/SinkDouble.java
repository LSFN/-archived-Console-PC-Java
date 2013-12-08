package org.lsfn.console_pc.data_store.sinks;

public class SinkDouble implements ISinkDouble {

	private Double data;
	
	public SinkDouble(double data) {
		this.data = data;
	}
	
	@Override
	public Double getData() {
		return data;
	}

}
