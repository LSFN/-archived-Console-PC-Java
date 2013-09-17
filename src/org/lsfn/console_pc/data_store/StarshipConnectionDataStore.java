package org.lsfn.console_pc.data_store;

import org.lsfn.console_pc.data_store.local.LocalInteger;
import org.lsfn.console_pc.data_store.local.LocalString;
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

public class StarshipConnectionDataStore implements IDataStore {

    private LocalString hostname;
    private LocalInteger port;
    
    public StarshipConnectionDataStore() {
        this.hostname = new LocalString("hostname");
        this.port = new LocalInteger(39460);
    }
    
    @Override
    public ISourceBoolean findSourceBoolean(IDataPath dataPath) {
        return null;
    }
    
    @Override
    public ISourceString findSourceString(IDataPath dataPath) {
        if(dataPath.topLevelMatch("hostname")) {
            return this.hostname;
        }
        return null;
    }

    @Override
    public ISourceInteger findSourceInteger(IDataPath dataPath) {
        if(dataPath.topLevelMatch("port")) {
            return this.port;
        }
        return null;
    }

    @Override
    public ISourceDouble findSourceDouble(IDataPath dataPath) {
        return null;
    }

    @Override
    public ISourceTrigger findSourceTrigger(IDataPath dataPath) {
        return null;
    }

    @Override
    public ISourcePolygon findSourcePolygon(IDataPath dataPath) {
        return null;
    }

    @Override
    public ISourceListPoint findSourceListPoint(IDataPath dataPath) {
        return null;
    }

    @Override
    public ISourcePoint findSourcePoint(IDataPath dataPath) {
        return null;
    }
    
    @Override
    public ISinkBoolean findSinkBoolean(IDataPath dataPath) {
        return null;
    }

    @Override
    public ISinkString findSinkString(IDataPath dataPath) {
        if(dataPath.topLevelMatch("hostname")) {
            return this.hostname;
        }
        return null;
    }

    @Override
    public ISinkInteger findSinkInteger(IDataPath dataPath) {
        if(dataPath.topLevelMatch("port")) {
            return this.port;
        }
        return null;
    }

    @Override
    public ISinkDouble findSinkDouble(IDataPath dataPath) {
        return null;
    }

    @Override
    public ISinkTrigger findSinkTrigger(IDataPath dataPath) {
        return null;
    }

    @Override
    public ISinkPolygon findSinkPolygon(IDataPath dataPath) {
        return null;
    }

    @Override
    public ISinkListPoint findSinkListPoint(IDataPath dataPath) {
        return null;
    }

    @Override
    public ISinkPoint findSinkPoint(IDataPath dataPath) {
        return null;
    }

}
