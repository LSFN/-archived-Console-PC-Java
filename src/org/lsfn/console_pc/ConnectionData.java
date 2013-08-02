package org.lsfn.console_pc;

import org.lsfn.console_pc.STS.STSdown;
import org.lsfn.console_pc.STS.STSup;

public class ConnectionData {

    private boolean connectedToNebula;
    
    private String hostname;
    private Integer port;
    private boolean desiredConnectionState;
    private boolean desiredConnectionStateChanged;
    
    public ConnectionData() {
        this.connectedToNebula = false;
        this.hostname = "localhost";
        this.port = 39461;
        this.desiredConnectionState = false;
        this.desiredConnectionStateChanged = false;
    }
    
    public boolean getConnectedToNebula() {
        return this.connectedToNebula;
    }
    
    public void processConnection(STSdown.Connection connection) {
        if(connection.hasConnected()) {
            this.connectedToNebula = connection.getConnected();
        }
    }
    
    public void connectToNebula(String host, Integer port) {
        this.hostname = host;
        this.port = port;
        this.desiredConnectionState = true;
        this.desiredConnectionStateChanged = true;
    }
    
    public void disconnectFromNebula() {
        this.desiredConnectionState = false;
        this.desiredConnectionStateChanged = true;
    }
    
    public STSup.Connection generateOutput() {
        if(this.desiredConnectionStateChanged) {
            STSup.Connection.Builder stsUpConnection = STSup.Connection.newBuilder();
            if(this.desiredConnectionState) {
                stsUpConnection.setConnectionCommand(STSup.Connection.ConnectionCommand.CONNECT);
                stsUpConnection.setHost(hostname);
                stsUpConnection.setPort(port);
            } else {
                stsUpConnection.setConnectionCommand(STSup.Connection.ConnectionCommand.DISCONNECT);
            }
            this.desiredConnectionStateChanged = false;
            return stsUpConnection.build();
        }
        return null;
    }
}
