package org.lsfn.console_pc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.lsfn.console_pc.StarshipConnection.ConnectionStatus;

public class ConsolePC {

    private StarshipConnection starshipConnection;
    private boolean keepGoing;
    
    public ConsolePC() {
        this.starshipConnection = null;
        this.keepGoing = true;
    }
    
    private void startNetwork(String host, int port) {
        this.starshipConnection = new StarshipConnection();
        ConnectionStatus status = this.starshipConnection.connect(host, port);
        if(status == ConnectionStatus.CONNECTED) {
            this.starshipConnection.run();
            System.out.println("Connected.");
        } else {
            System.out.println("Connection failed.");
        }
    }
    
    private void printHelp() {
        System.out.println("Console-PC commands:");
        System.out.println("\thelp   : print this help text.");
        System.out.println("\tconnect <host> <port> : connects to the server on the given host and port.");
        System.out.println("\texit   : end this program.");
    }
    
    private void processCommand(String commandStr) {
        String[] commandParts = commandStr.split(" ");
         
        if(commandParts[0].equals("connect")) {
            startNetwork(commandParts[1], Integer.parseInt(commandParts[2]));
        } else if(commandParts[0].equals("exit")) {
            this.keepGoing = false;
        } else if(commandParts[0].equals("help")) {
            printHelp();
        } else {
            System.out.println("You're spouting gibberish. Please try English.");
        }
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
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        ConsolePC consolePC = new ConsolePC();
        consolePC.run(args);
    }

}
