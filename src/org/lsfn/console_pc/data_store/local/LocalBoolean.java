package org.lsfn.console_pc.data_store.local;

import org.lsfn.console_pc.data_store.sinks.ISinkBoolean;
import org.lsfn.console_pc.data_store.sources.ISourceBoolean;

public class LocalBoolean implements ISinkBoolean, ISourceBoolean {

	private Boolean data;
	
	public LocalBoolean(boolean state) {
		this.data = state;
	}
	
	@Override
	public void setBoolean(boolean state) {
		this.data = state;
	}

	@Override
	public Boolean getData() {
		return data;
	}

}
