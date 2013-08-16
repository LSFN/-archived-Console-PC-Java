package org.lsfn.console_pc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFrame;

import org.lsfn.console_pc.data_management.DataManager;

public class ConsolePC extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 5344546375757215850L;
    private DataManager dataManager;
    private boolean keepGoing;
    
    public ConsolePC() {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setFocusable(true);
        //this.setUndecorated(true);
        this.setSize(WIDTH, HEIGHT);
        this.setVisible(true);
        this.createBufferStrategy(2);
        this.dataManager = new DataManager();
        this.add(this.dataManager.getScreenManager());
        this.pack();
        this.addKeyListener(this.dataManager.getInputManager());
        
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
        
        this.dataManager.start();
        
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
