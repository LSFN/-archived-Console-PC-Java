package org.lsfn.console_pc.data_management;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.lsfn.console_pc.STS.STSdown;
import org.lsfn.console_pc.STS.STSup;
import org.lsfn.console_pc.StarshipConnection;
import org.lsfn.console_pc.data_management.elements.ControlledData;
import org.lsfn.console_pc.data_management.elements.DataSource;
import org.lsfn.console_pc.data_management.elements.TypeableInteger;
import org.lsfn.console_pc.data_management.elements.TypeableString;

public class StarshipConnectionData {

    private StarshipConnection starshipConnection;
    
    private TypeableString hostname;
    private TypeableInteger port;
    private StarshipConnectionConnectClickListener connectListener;
    private StarshipConnectionDisconnectClickListener disconnectListener;
    
    public StarshipConnectionData(StarshipConnection starshipConnection) {
        this.starshipConnection = starshipConnection;
        this.hostname = new TypeableString("localhost");
        this.port = new TypeableInteger(39460);
        this.connectListener = new StarshipConnectionConnectClickListener(this); 
        this.disconnectListener = new StarshipConnectionDisconnectClickListener(this);
    }
    
    public void connect() {
        this.starshipConnection.connect(this.hostname.getData(), this.port.getData());
    }
    
    public boolean isConnected() {
        return this.starshipConnection.isConnected();
    }
    
    public void disconnect() {
        this.starshipConnection.disconnect();
    }

    public DataSource getDataSourceFromPath(String dataPath) {
        if(dataPath.equals("hostname")) {
            return this.hostname;
        } else if(dataPath.equals("port")) {
            return this.port;
        }
        return null;
    }

    public ControlledData getControlledDataFromPath(String dataPath) {
        if(dataPath.equals("hostname")) {
            return this.hostname;
        } else if(dataPath.equals("port")) {
            return this.port;
        } else if(dataPath.equals("connect")) {
            return this.connectListener;
        } else if(dataPath.equals("disconnect")) {
            return this.disconnectListener;
        }
        return null;
    }
    
}
