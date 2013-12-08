package org.lsfn.console_pc.data_store.local;

import org.lsfn.console_pc.data_store.sinks.ISinkDouble;
import org.lsfn.console_pc.data_store.sources.ISourceDouble;

public class LocalDouble implements ISourceDouble, ISinkDouble {

	private Double data;
	
	public LocalDouble(double data) {
		this.data = data;
	}
	
	@Override
	public void setData(double data) {
		this.data = data;
	}

	@Override
	public Double getData() {
		return data;
	}
	
}
