package org.lsfn.console_pc;

import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.lsfn.console_pc.PilotingDisplay.DisplayState;
import org.lsfn.console_pc.StarshipConnection.ConnectionStatus;

public class ConsolePC {

    private StarshipConnection starshipConnection;
    private PilotingDisplay pilotingDisplay;
    private boolean keepGoing;
    
    public ConsolePC() {
        this.starshipConnection = null;
        this.pilotingDisplay = null;
        this.keepGoing = true;
    }
    
    public void startStarshipClient(String host, Integer port) {
        this.starshipConnection = new StarshipConnection();
        System.out.println("Connecting...");
        ConnectionStatus status = ConnectionStatus.DISCONNECTED;
        if(host == null || port == null) {
            status = this.starshipConnection.connect();
        } else {
            status = this.starshipConnection.connect(host, port);
        }
        if(status == ConnectionStatus.CONNECTED) {
            this.starshipConnection.start();
            System.out.println("Connected.");
            this.pilotingDisplay.changeDisplayState(DisplayState.LOBBY);
        } else {
            System.out.println("Connection failed.");
        }
    }
    

    private void stopStarshipClient() {
        if(this.starshipConnection != null) {
            if(this.starshipConnection.getConnectionStatus() == ConnectionStatus.CONNECTED) {
                this.starshipConnection.disconnect();
            }
        }
        System.out.println("Disconnected.");
    }
    
    private void printHelp() {
        System.out.println("Console-PC commands:");
        System.out.println("\thelp                  : print this help text.");
        System.out.println("\tconnect <host> <port> : connects to the Starship on the given host and port.");
        System.out.println("\tconnect               : connects to the Starship on the default host and port.");
        System.out.println("\tdisconnect            : disconnects from the Starship if connected.");
        System.out.println("\tdisplay start         : starts the GUI.");
        System.out.println("\tdisplay stop          : stops the GUI.");
        System.out.println("\texit                  : end this program.");
    }
    
    private void startDisplay() {
        this.pilotingDisplay = new PilotingDisplay(this, this.starshipConnection);
        this.pilotingDisplay.start();
    }

    public void stopDisplay() {
        this.pilotingDisplay.stop();
        this.pilotingDisplay.dispatchEvent(new WindowEvent(this.pilotingDisplay, WindowEvent.WINDOW_CLOSING));
    }
    
    private void processCommand(String commandStr) {
        String[] commandParts = commandStr.split(" ");
         
        if(commandParts[0].equals("connect")) {
            if(commandParts.length == 3) {
                startStarshipClient(commandParts[1], Integer.parseInt(commandParts[2]));
            } else if(commandParts.length == 1) {
                startStarshipClient(null, null);
            }
        } else if(commandParts[0].equals("disconnect")) {
            stopStarshipClient();
        } else if(commandParts[0].equals("exit")) {
            this.keepGoing = false;
        } else if(commandParts[0].equals("help")) {
            printHelp();
        } else if(commandParts[0].equals("display")) {
            if(commandParts[1].equals("start")) {
                startDisplay();
            } else if (commandParts[1].equals("stop")){
                stopDisplay();
            } else {
                System.out.println("The display can't do that, try something within the bounds of feasibility.");
            }
        } else {
            System.out.println("You're spouting gibberish. Please try English.");
        }
    }

    public StarshipConnection getStarshipConnection() {
        return this.starshipConnection;
    }
    
    public void run(String[] args) {
        printHelp();
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(this.keepGoing) {
            try {
                processCommand(reader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        // Close up the threads
        if(this.starshipConnection != null) {
            if(this.starshipConnection.getConnectionStatus() == ConnectionStatus.CONNECTED) {
                System.out.println("Disconnecting from Starship...");
                this.starshipConnection.disconnect();
            }
            if(this.starshipConnection.isAlive()) {
                try {
                    System.out.println("Joining listener thread...");
                    this.starshipConnection.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        ConsolePC consolePC = new ConsolePC();
        consolePC.run(args);
    }

}
