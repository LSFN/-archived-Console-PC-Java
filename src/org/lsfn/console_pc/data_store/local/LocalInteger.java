package org.lsfn.console_pc.data_store.local;

import org.lsfn.console_pc.data_store.sinks.ISinkInteger;
import org.lsfn.console_pc.data_store.sources.ISourceInteger;

public class LocalInteger implements ISourceInteger, ISinkInteger {

    private Integer data;
    
    public LocalInteger(Integer data) {
        this.data = data;
    }
}
