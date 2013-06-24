package org.lsfn.console_pc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.lsfn.console_pc.STS.*;

public class StarshipConnection extends Thread {

    private static final String defaultHost = "localhost";
    private static final Integer defaultPort = 39460;
    private static final Integer pollWait = 50;
    
    private Socket starshipSocket;
    private BufferedInputStream starshipInput;
    private BufferedOutputStream starshipOutput;
    private ArrayList<STSdown> starshipMessages;
    
    private String host;
    private Integer port;
    
    public enum ConnectionStatus {
        CONNECTED,
        DISCONNECTED
    }
    private ConnectionStatus connectionStatus;
    
    public StarshipConnection() {
        super();
        this.starshipSocket = null;
        this.starshipInput = null;
        this.starshipOutput = null;
        this.starshipMessages = null;
        this.host = null;
        this.port = null;
        this.connectionStatus = ConnectionStatus.DISCONNECTED;
    }
    
    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }
    
    public ConnectionStatus getConnectionStatus() {
        return connectionStatus;
    }
    
    private void clearConnection() {
        this.starshipSocket = null;
        this.starshipInput = null;
        this.starshipOutput = null;
        this.starshipMessages = null;
    }
    
    /**
     * Closes the connection to the remote host.
     * Designed for cases where we don't care if closing the connection fails,
     * we are getting out of this connection one way or the other...
     * ...like a bad relationship.
     */
    private void closeConnection() {
        try {
            this.starshipOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Connects to the given host on the given port.
     * Will not connect to another host whilst connected.
     * Will not change the connection status from previous disconnected status if connection fails.
     * @param host The host to connect to.
     * @param port The port to connect on.
     * @return The new connection status of the connection.
     */
    public ConnectionStatus connect(String host, Integer port) {
        if(this.connectionStatus != ConnectionStatus.CONNECTED) {
            this.host = host;
            this.port = port;
            try {
                this.starshipSocket = new Socket(this.host, this.port);
                this.starshipInput = new BufferedInputStream(starshipSocket.getInputStream());
                this.starshipOutput = new BufferedOutputStream(starshipSocket.getOutputStream());
                this.starshipMessages = new ArrayList<STSdown>();
                this.connectionStatus = ConnectionStatus.CONNECTED;
            } catch (IOException e) {
                e.printStackTrace();
                clearConnection();
            }
        }
        return this.connectionStatus;
    }
    
    public ConnectionStatus connect() {
        return this.connect(defaultHost, defaultPort);
    }
    
    /**
     * Disconnects from the remote host.
     * @return The new connection status of the connection.
     */
    public ConnectionStatus disconnect() {
        closeConnection();
        clearConnection();
        this.connectionStatus = ConnectionStatus.DISCONNECTED;
        return this.connectionStatus;
    }
    
    /**
     * Sends a message to the Starship that this class is connected to.
     * Won't sent a message when disconnected
     * @param upMessage The message to be sent.
     * @return The new connection status of the connection.
     */
    public ConnectionStatus sendMessageToStarship(STSup upMessage) {
        if(this.connectionStatus == ConnectionStatus.CONNECTED) {
            try {
                upMessage.writeDelimitedTo(this.starshipOutput);
            } catch (IOException e) {
                e.printStackTrace();
                closeConnection();
                clearConnection();
                this.connectionStatus = ConnectionStatus.DISCONNECTED;
            }
        }
        return this.connectionStatus;
    }
    
    public synchronized List<STSdown> receiveMessagesFromStarship() {
        List<STSdown> result = new ArrayList<STSdown>(this.starshipMessages);
        this.starshipMessages.clear();
        return result;
    }

    private synchronized void addMessageToBuffer(STSdown downMessage) {
        this.starshipMessages.add(downMessage);
    }
    
    @Override
    public void run() {
        while(this.connectionStatus == ConnectionStatus.CONNECTED) {
            try {
                if(this.starshipInput.available() > 0) {
                    STSdown downMessage = STSdown.parseDelimitedFrom(this.starshipInput);
                    addMessageToBuffer(downMessage);
                }
            } catch (IOException e) {
                e.printStackTrace();
                closeConnection();
                clearConnection();
            }
            try {
                Thread.sleep(pollWait);
            } catch (InterruptedException e) {
                // An interrupt indicates that something has happened to the connection
                // This loop will now probably terminate.
            }
        }
    }
}
