package org.lsfn.console_pc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFrame;

import org.lsfn.console_pc.data_store.DataStore;
import org.lsfn.console_pc.specialised_display.SpecialisedDisplayManager;
import org.lsfn.console_pc.synchoniser.Synchroniser;

public class ConsolePC extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 5344546375757215850L;
    private boolean keepGoing;
    private SpecialisedDisplayManager displayManager;
    private Thread displayManagerThread;
    private Synchroniser synchroniser;
    private DataStore dataStore;
    
    public ConsolePC() {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setFocusable(true);
        //this.setUndecorated(true); // For fullscreen awesomeness
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("LSFN");
        this.setVisible(true);
        this.createBufferStrategy(2);

        this.keepGoing = true;
    }
    
    private void printHelp() {
        System.out.println("Console-PC commands:");
        System.out.println("\thelp                  : print this help text.");
        System.out.println("\texit                  : end this program.");
    }
    
    private void processCommand(String commandStr) {
        String[] commandParts = commandStr.split(" ");
         
        if(commandParts[0].equals("exit")) {
            this.keepGoing = false;
        } else if(commandParts[0].equals("help")) {
            printHelp();
        } else {
            System.out.println("You're spouting gibberish. Please try English.");
        }
    }

    public void run(String[] args) {
        printHelp();
        
        this.dataStore = new DataStore();
        this.synchroniser = new Synchroniser(this.dataStore);
        this.synchroniser.start();
        this.displayManager = new SpecialisedDisplayManager(this, this.dataStore);
        this.displayManagerThread = new Thread(this.displayManager);
        this.displayManagerThread.start();
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(this.keepGoing) {
            try {
                processCommand(reader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        // TODO perform sensible shutdown
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        ConsolePC consolePC = new ConsolePC();
        consolePC.run(args);
    }

}
