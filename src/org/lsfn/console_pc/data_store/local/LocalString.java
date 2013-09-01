package org.lsfn.console_pc.data_store.local;

import org.lsfn.console_pc.data_store.sinks.ISinkString;
import org.lsfn.console_pc.data_store.sources.ISourceString;

public class LocalString implements ISourceString, ISinkString {

    private String data;
    
    public LocalString(String data) {
        this.data = data;
    }
}
