package org.lsfn.console_pc.data_store;

import org.lsfn.common.STS.STSdown;
import org.lsfn.common.STS.STSup;
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
import org.lsfn.console_pc.data_store.sinks.SinkBoolean;
import org.lsfn.console_pc.data_store.sources.ISourceBoolean;
import org.lsfn.console_pc.data_store.sources.ISourceDouble;
import org.lsfn.console_pc.data_store.sources.ISourceInteger;
import org.lsfn.console_pc.data_store.sources.ISourcePoint;
import org.lsfn.console_pc.data_store.sources.ISourceString;
import org.lsfn.console_pc.data_store.sources.ISourceTrigger;

public class NebulaConnectionDataStore implements IDataStore {

	private LocalString hostname;
	private LocalInteger port;
	private SinkBoolean connected;
	private LocalTrigger connect;
	private LocalTrigger disconnect;
	
	public NebulaConnectionDataStore() {
		this.hostname = new LocalString("localhost");
		this.port = new LocalInteger(39461);
		this.connected = new SinkBoolean(false);
		this.connect = new LocalTrigger();
		this.disconnect = new LocalTrigger();
	}
	
	public void processInput(STSdown.Connection connection) {
		if(connection.hasConnected()) {
			this.connected.setData(connection.getConnected());
		}
	}
	
	public STSup.Connection generateOutput() {
		// The terms in the && statement must go this way round
		if(connect.isTriggered() && !connected.getData()) {
			STSup.Connection.Builder stsUpConnection = STSup.Connection.newBuilder();
			stsUpConnection.setConnectionCommand(STSup.Connection.ConnectionCommand.CONNECT);
			stsUpConnection.setHost(hostname.getData());
			stsUpConnection.setPort(port.getData());
			return stsUpConnection.build();
		} else if(disconnect.isTriggered() && connected.getData()) {
			STSup.Connection.Builder stsUpConnection = STSup.Connection.newBuilder();
			stsUpConnection.setConnectionCommand(STSup.Connection.ConnectionCommand.DISCONNECT);
			return stsUpConnection.build();
		}
		return null;
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
		if(dataPath.topLevelMatch("connectNebula")) {
            return this.connect;
        } else if(dataPath.topLevelMatch("disconnectNebula")) {
        	return this.disconnect;
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
		return null;
	}

	@Override
	public ISinkPoint findSinkPoint(IDataPath dataPath) {
		return null;
	}

}
