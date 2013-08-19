package org.lsfn.console_pc.data_management;

import org.lsfn.console_pc.STS.STSdown;
import org.lsfn.console_pc.STS.STSup;
import org.lsfn.console_pc.data_management.elements.BooleanDataSource;
import org.lsfn.console_pc.data_management.elements.ControlledData;
import org.lsfn.console_pc.data_management.elements.DataSource;
import org.lsfn.console_pc.data_management.elements.TypeableInteger;
import org.lsfn.console_pc.data_management.elements.TypeableString;
import org.lsfn.console_pc.data_management.elements.UpdatableBoolean;

public class NebulaConnectionData {

    private BooleanDataSource connectedToNebula;
    
    private TypeableString hostname;
    private TypeableInteger port;
    private UpdatableBoolean desiredConnectionState;
    
    public NebulaConnectionData() {
        this.connectedToNebula = new BooleanDataSource(false);
        
        this.hostname = new TypeableString("localhost");
        this.port = new TypeableInteger(39461);
        this.desiredConnectionState = new UpdatableBoolean(false);
    }
    
    public boolean isConnected() {
        return this.connectedToNebula.getData();
    }
    
    public void processConnection(STSdown.Connection connection) {
        if(connection.hasConnected()) {
            this.connectedToNebula.setData(connection.getConnected());
        }
    }    
    
    public STSup.Connection generateOutput() {
        if(this.desiredConnectionState.flagRaised()) {
            STSup.Connection.Builder stsUpConnection = STSup.Connection.newBuilder();
            if(this.desiredConnectionState.getData()) {
                stsUpConnection.setConnectionCommand(STSup.Connection.ConnectionCommand.CONNECT);
                stsUpConnection.setHost(hostname.getData());
                stsUpConnection.setPort(port.getData());
            } else {
                stsUpConnection.setConnectionCommand(STSup.Connection.ConnectionCommand.DISCONNECT);
            }
            this.desiredConnectionState.resetFlag();
            return stsUpConnection.build();
        }
        return null;
    }
    
    // TODO make an interface for these functions
    public DataSource getDataSourceFromPath(String dataPath) {
        if(dataPath.equals("hostname")) {
            return this.hostname;
        } else if(dataPath.equals("port")) {
            return this.port;
        } else if(dataPath.equals("connected")) {
            return this.connectedToNebula;
        }
        return null;
    }

    public ControlledData getControlledDataFromPath(String dataPath) {
        if(dataPath.equals("hostname")) {
            return this.hostname;
        } else if(dataPath.equals("port")) {
            return this.port;
        } else if(dataPath.equals("connection")) {
            return this.desiredConnectionState;
        }
        return null;
    }
}
