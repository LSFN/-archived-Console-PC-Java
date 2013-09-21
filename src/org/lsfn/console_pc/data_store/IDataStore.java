package org.lsfn.console_pc.data_store;

import org.lsfn.console_pc.data_store.sinks.ISinkBoolean;
import org.lsfn.console_pc.data_store.sinks.ISinkDouble;
import org.lsfn.console_pc.data_store.sinks.ISinkInteger;
import org.lsfn.console_pc.data_store.sinks.ISinkListPoint;
import org.lsfn.console_pc.data_store.sinks.ISinkPoint;
import org.lsfn.console_pc.data_store.sinks.ISinkPolygon;
import org.lsfn.console_pc.data_store.sinks.ISinkString;
import org.lsfn.console_pc.data_store.sinks.ISinkTrigger;
import org.lsfn.console_pc.data_store.sources.ISourceBoolean;
import org.lsfn.console_pc.data_store.sources.ISourceDouble;
import org.lsfn.console_pc.data_store.sources.ISourceInteger;
import org.lsfn.console_pc.data_store.sources.ISourceListPoint;
import org.lsfn.console_pc.data_store.sources.ISourcePoint;
import org.lsfn.console_pc.data_store.sources.ISourcePolygon;
import org.lsfn.console_pc.data_store.sources.ISourceString;
import org.lsfn.console_pc.data_store.sources.ISourceTrigger;

public interface IDataStore {

    // Sources
    public ISourceBoolean findSourceBoolean(IDataPath dataPath);
    public ISourceString findSourceString(IDataPath dataPath);
    public ISourceInteger findSourceInteger(IDataPath dataPath);
    public ISourceDouble findSourceDouble(IDataPath dataPath);
    public ISourceTrigger findSourceTrigger(IDataPath dataPath);
    public ISourcePoint findSourcePoint(IDataPath dataPath);
    
    // Sinks
    public ISinkBoolean findSinkBoolean(IDataPath dataPath);
    public ISinkString findSinkString(IDataPath dataPath);
    public ISinkInteger findSinkInteger(IDataPath dataPath);
    public ISinkDouble findSinkDouble(IDataPath dataPath);
    public ISinkTrigger findSinkTrigger(IDataPath dataPath);
    public ISinkPoint findSinkPoint(IDataPath dataPath);
    
}
