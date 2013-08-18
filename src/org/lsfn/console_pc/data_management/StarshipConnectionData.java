package org.lsfn.console_pc.data_management;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.lsfn.console_pc.STS.STSdown;
import org.lsfn.console_pc.STS.STSup;
import org.lsfn.console_pc.data_management.elements.BooleanDataSource;
import org.lsfn.console_pc.data_management.elements.ControlledData;
import org.lsfn.console_pc.data_management.elements.DataSource;
import org.lsfn.console_pc.data_management.elements.TypeableInteger;
import org.lsfn.console_pc.data_management.elements.TypeableString;

public class StarshipConnectionData extends Thread {

    private static final Integer tickInterval = 50;
    private static final long timeout = 5000;
    private static final long timeBetweenPings = 3000;
    private static final STSup pingMessage = STSup.newBuilder().build();
    
    private Socket starshipSocket;
    private BufferedInputStream starshipInput;
    private BufferedOutputStream starshipOutput;
    private List<STSdown> starshipMessages;
    private long timeLastMessageReceived;
    private long timeLastMessageSent;
    private boolean connected;
    
    private TypeableString hostname;
    private TypeableInteger port;
    private StarshipConnectionConnectClickListener connectListener;
    
    public StarshipConnectionData() {
        this.starshipSocket = null;
        this.starshipInput = null;
        this.starshipOutput = null;
        this.starshipMessages = new ArrayList<STSdown>();
        this.timeLastMessageSent = 0;
        this.timeLastMessageReceived = 0;
        this.connected = false;
        
        this.hostname = new TypeableString("localhost");
        this.port = new TypeableInteger(39460);
        this.connectListener = new StarshipConnectionConnectClickListener(this); 
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
        }
        return null;
    }
    
    public void connect() {
        this.connected = false;
        try {
            this.starshipSocket = new Socket(this.hostname.getData(), this.port.getData());
            this.starshipInput = new BufferedInputStream(starshipSocket.getInputStream());
            this.starshipOutput = new BufferedOutputStream(starshipSocket.getOutputStream());
            this.connected = false;
            this.timeLastMessageReceived = System.currentTimeMillis();
            this.timeLastMessageSent = System.currentTimeMillis();
            this.start();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean isConnected() {
        if(this.connected && System.currentTimeMillis() >= this.timeLastMessageReceived + timeout) {
            this.disconnect();
        }
        return this.connected;
    }
    
    public void disconnect() {
        try {
            this.starshipSocket.close();
        } catch (IOException e) {
            // We don't care
            e.printStackTrace();
        }
        this.connected = false;
    }
    
    public void sendMessageToStarship(STSup upMessage) {
        if(this.connected) {
            try {
                upMessage.writeDelimitedTo(this.starshipOutput);
                this.starshipOutput.flush();
                this.timeLastMessageSent = System.currentTimeMillis();
            } catch (IOException e) {
                e.printStackTrace();
                this.connected = false;
            }
        }
    }
    
    public synchronized List<STSdown> receiveMessagesFromStarship() {
        List<STSdown> result = this.starshipMessages;
        this.starshipMessages = new ArrayList<STSdown>();
        return result;
    }

    private synchronized void addMessageToBuffer(STSdown downMessage) {
        this.starshipMessages.add(downMessage);
    }
    
    @Override
    public void run() {
        while(this.connected) {
            try {
                if(this.starshipInput.available() > 0) {
                    this.timeLastMessageReceived = System.currentTimeMillis();
                    STSdown downMessage = STSdown.parseDelimitedFrom(this.starshipInput);
                    // Messages of size 0 are keep alives
                    if(downMessage.getSerializedSize() > 0) {
                        addMessageToBuffer(downMessage);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                this.connected = false;
            }
            
            if(System.currentTimeMillis() >= this.timeLastMessageReceived + timeout) {
                disconnect();
            } else if(System.currentTimeMillis() >= this.timeLastMessageSent + timeBetweenPings) {
                sendMessageToStarship(pingMessage);
            }
            
            try {
                Thread.sleep(tickInterval);
            } catch (InterruptedException e) {
                // An interrupt indicates that something has happened to the connection
                // This loop will now probably terminate.
            }
        }
    }
}