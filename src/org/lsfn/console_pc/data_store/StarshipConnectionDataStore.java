package org.lsfn.console_pc.data_store;

import org.lsfn.console_pc.data_store.local.LocalBoolean;
import org.lsfn.console_pc.data_store.local.LocalInteger;
import org.lsfn.console_pc.data_store.local.LocalString;
import org.lsfn.console_pc.data_store.local.LocalTrigger;
import org.lsfn.console_pc.data_store.sinks.ISinkBoolean;
import org.lsfn.console_pc.data_store.sinks.ISinkDouble;
import org.lsfn.console_pc.data_store.sinks.ISinkInteger;
import org.lsfn.console_pc.data_store.sinks.ISinkPoint;
import org.lsfn.console_pc.data_store.sinks.ISinkString;
import org.lsfn.console_pc.data_store.sinks.ISinkStringList;
import org.lsfn.console_pc.data_store.sinks.ISinkTrigger;
import org.lsfn.console_pc.data_store.sources.ISourceBoolean;
import org.lsfn.console_pc.data_store.sources.ISourceDouble;
import org.lsfn.console_pc.data_store.sources.ISourceInteger;
import org.lsfn.console_pc.data_store.sources.ISourcePoint;
import org.lsfn.console_pc.data_store.sources.ISourceString;
import org.lsfn.console_pc.data_store.sources.ISourceTrigger;

public class StarshipConnectionDataStore implements IDataStore {

    private LocalString hostname;
    private LocalInteger port;
    private LocalTrigger connect;
    private LocalTrigger disconnect;
    private LocalBoolean connected;
    
    public StarshipConnectionDataStore() {
        this.hostname = new LocalString("localhost");
        this.port = new LocalInteger(39460);
        this.connect = new LocalTrigger();
        this.disconnect = new LocalTrigger();
        this.connected = new LocalBoolean(false);
    }
    
    @Override
    public ISourceBoolean findSourceBoolean(IDataPath dataPath) {
    	if(dataPath.topLevelMatch("connected")) {
            return this.connected;
        }
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
    	if(dataPath.topLevelMatch("connect")) {
    		return connect;
    	} else if(dataPath.topLevelMatch("disconnect")) {
    		return disconnect;
    	}
        return null;
    }

    @Override
    public ISourcePoint findSourcePoint(IDataPath dataPath) {
        return null;
    }
    
    @Override
    public ISinkBoolean findSinkBoolean(IDataPath dataPath) {
    	if(dataPath.topLevelMatch("connected")) {
            return this.connected;
        }
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
	public ISinkStringList findSinkStringList(IDataPath dataPath) {
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
    	if(dataPath.topLevelMatch("connect")) {
    		return connect;
    	} else if(dataPath.topLevelMatch("disconnect")) {
    		return disconnect;
    	}
        return null;
    }

    @Override
    public ISinkPoint findSinkPoint(IDataPath dataPath) {
        return null;
    }

}
