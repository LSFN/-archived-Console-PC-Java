package org.lsfn.console_pc.data_management;

import org.lsfn.console_pc.STS.STSdown;
import org.lsfn.console_pc.STS.STSup;

public class ConnectionData {

    private UnchangingBoolean connectedToNebula;
    
    private UpdatableString hostname;
    private UpdatableInteger port;
    private UpdatableBoolean desiredConnectionState;
    
    public ConnectionData() {
        this.connectedToNebula = new UnchangingBoolean(false);
        
        this.hostname = new UpdatableString("localhost");
        this.port = new UpdatableInteger(39461);
        this.desiredConnectionState = new UpdatableBoolean(false);
    }
    
    public void processConnection(STSdown.Connection connection) {
        if(connection.hasConnected()) {
            this.connectedToNebula.setData(connection.getConnected());
        }
    }
    
    public UnchangingDataPointer getDataLocation(String path) {
        if(path.equals("connectedToNebula")) {
            return this.connectedToNebula;
        }
        return null;
    }
    
    
    public STSup.Connection generateOutput() {
        if(this.desiredConnectionState.isFlagSet()) {
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
}
