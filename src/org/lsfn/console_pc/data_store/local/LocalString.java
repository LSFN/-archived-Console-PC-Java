package org.lsfn.console_pc.data_store.local;

import org.lsfn.console_pc.data_store.sinks.ISinkString;
import org.lsfn.console_pc.data_store.sources.ISourceString;

public class LocalString implements ISourceString, ISinkString {

    private String data;
    
    public LocalString(String data) {
        this.data = data;
    }

    @Override
    public String getData() {
        return data;
    }

    @Override
    public void appendCharacter(char c) {
        this.data += c;
    }

    @Override
    public void deleteCharacter() {
        this.data = this.data.substring(0, this.data.length() - 1);
    }
}
