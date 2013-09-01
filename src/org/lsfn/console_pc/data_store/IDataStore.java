package org.lsfn.console_pc.data_store;

import org.lsfn.console_pc.data_store.sinks.ISinkBoolean;
import org.lsfn.console_pc.data_store.sinks.ISinkInteger;
import org.lsfn.console_pc.data_store.sinks.ISinkString;
import org.lsfn.console_pc.data_store.sources.ISourceBoolean;
import org.lsfn.console_pc.data_store.sources.ISourceInteger;
import org.lsfn.console_pc.data_store.sources.ISourceString;

public interface IDataStore {

    // Sources
    public ISourceBoolean findSourceBoolean(IDataPath dataPath);
    public ISourceString findSourceString(IDataPath dataPath);
    public ISourceInteger findSourceInteger(IDataPath dataPath);
    
    // Sinks
    public ISinkBoolean findSinkBoolean(IDataPath dataPath);
    public ISinkString findSinkString(IDataPath dataPath);
    public ISinkInteger findSinkInteger(IDataPath dataPath);
    
}
