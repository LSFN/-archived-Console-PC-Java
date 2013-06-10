package org.lsfn.console_pc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.lsfn.console_pc.STS.STSdown;
import org.lsfn.console_pc.STS.STSup;

/**
 * The general intention here is that this class runs as a thread separate from the main program.
 * Once the networking is connected to a host, the thread may be run to perform the listening.
 * Input from the Starship will be processed into a protocol buffer object and placed in a buffer for further use.
 * Output going to the Starship is given to this class in the appropriate protocol buffer object.
 * 
 * @author Lukeus_Maximus
 *
 */
public class Networking extends Thread {

    private Socket starshipSocket;
    private BufferedInputStream starshipInput;
    private BufferedOutputStream starshipOutput;
    private ArrayList<STSdown> starshipMessages;
    
    private String host;
    private InetAddress hostAddr;
    private Integer port;
    
    /**
     * Creates a new networking object.
     */
    public Networking() {
        super();
        this.starshipSocket = null;
        this.starshipInput = null;
        this.starshipOutput = null;
        this.starshipMessages = null;
        this.host = null;
        this.hostAddr = null;
        this.port = null;
    }
    
    /**
     * Sets the connection endpoint info.
     * @param host The host to connect to.
     * @param port The port to connect on.
     */
    public void setStarshipAddress(String host, Integer port) {
        this.host = host;
        this.hostAddr = null;
        this.port = port;
    }
    
    /**
     * Sets the connection endpoint info.
     * @param host The host to connect to.
     * @param port The port to connect on.
     */
    public void setStarshipAddress(InetAddress host, Integer port) {
        this.host = null;
        this.hostAddr = host;
        this.port = port;
    }
    
    /**
     * Sends a message to the Starship if connected.
     * @param upMessage The message to be sent.
     */
    public void writeMessage(STSup upMessage) {
        if(this.isConnected()) {
            try {
                upMessage.writeDelimitedTo(this.starshipOutput);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Gets all the messages received from the Starship in the buffer.
     * Clears the buffer after this call.
     * @return A list of messages from the Starship.
     */
    public synchronized List<STSdown> readMessages() {
        ArrayList<STSdown> result = new ArrayList<STSdown>(this.starshipMessages);
        this.starshipMessages.clear();
        return result;
    }
    
    /**
     * Adds a message received from the Starship to the buffer.
     * @param downmessage The message received.
     */
    private synchronized void addMessageToBuffer(STSdown downmessage) {
        this.starshipMessages.add(downmessage);
    }
    
    /**
     * Indicates whether the socket is connected or not.
     * @return Returns true if connected, false otherwise.
     */
    public boolean isConnected() {
        if(this.starshipSocket == null) return false;
        return this.starshipSocket.isConnected();
    }
    
    /**
     * Connects to the host.
     */
    public void connect() {
        if(this.host != null) {
            try {
                this.starshipSocket = new Socket(this.host, this.port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(this.hostAddr != null) {
            try {
                this.starshipSocket = new Socket(this.hostAddr, this.port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(this.isConnected()) {
            try {
                this.starshipInput = new BufferedInputStream(starshipSocket.getInputStream());
                this.starshipOutput = new BufferedOutputStream(starshipSocket.getOutputStream());
                this.starshipMessages = new ArrayList<STSdown>();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Disconnects from the host.
     */
    public void disconnect() {
        try {
            this.starshipSocket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.starshipSocket = null;
    }
    
    
    @Override
    /**
     * Listens to the host while it is connected.
     * Ends once host is disconnected
     */
    public void run() {
        while(this.isConnected()) {
            try {
                STSdown nextMessage = STSdown.parseDelimitedFrom(starshipInput);
                addMessageToBuffer(nextMessage);
            } catch (IOException e) {
                e.printStackTrace();
                // Disconnects in the case that the exception doesn't cause a disconnection.
                if(this.isConnected()) {
                    try {
                        this.starshipSocket.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    this.starshipSocket = null;
                }
                this.starshipInput = null;
                this.starshipOutput = null;
                this.starshipMessages = null;
            }
        }
    }

}
