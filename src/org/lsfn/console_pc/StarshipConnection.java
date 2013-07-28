package org.lsfn.console_pc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.lsfn.console_pc.STS.*;
import org.lsfn.console_pc.STS.STSdown.Join.Response;

public class StarshipConnection extends Thread {

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
    
    public StarshipConnection() {
        this.starshipSocket = null;
        this.starshipInput = null;
        this.starshipOutput = null;
        this.starshipMessages = new ArrayList<STSdown>();
        this.timeLastMessageSent = 0;
        this.timeLastMessageReceived = 0;
        this.connected = false;
    }
    
    public boolean connect(String host, Integer port) {
        this.connected = false;
        try {
            this.starshipSocket = new Socket(host, port);
            this.starshipInput = new BufferedInputStream(starshipSocket.getInputStream());
            this.starshipOutput = new BufferedOutputStream(starshipSocket.getOutputStream());
            this.connected = true;
            this.timeLastMessageReceived = System.currentTimeMillis();
            this.timeLastMessageSent = System.currentTimeMillis();
        } catch(IOException e) {
            e.printStackTrace();
        }
        
        return this.connected;
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
        List<STSdown> result = new ArrayList<STSdown>(this.starshipMessages);
        this.starshipMessages.clear();
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
            
            if(System.currentTimeMillis() >= this.timeLastMessageSent + timeBetweenPings) {
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
