package org.lsfn.console_pc.data_store.local;

import org.lsfn.console_pc.data_store.sinks.ISinkInteger;
import org.lsfn.console_pc.data_store.sources.ISourceInteger;

public class LocalInteger implements ISourceInteger, ISinkInteger {

    private Integer data;
    
    public LocalInteger(Integer data) {
        this.data = data;
    }

    @Override
    public void addTypedDigit(char c) {
        this.data = (this.data * 10) + ((int)c - (int)'0');
    }

    @Override
    public void deleteTypedDigit() {
        this.data /= 10;
    }

	@Override
	public Integer getData() {
		return data;
	}
}
