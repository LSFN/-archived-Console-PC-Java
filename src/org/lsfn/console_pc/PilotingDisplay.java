package org.lsfn.console_pc;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;

import javax.swing.Timer;
import javax.swing.JFrame;

import org.lsfn.console_pc.STS.STSup;

public class PilotingDisplay extends JFrame {
    
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 1024;
    private static final int tickInterval = 50;
    
    private StarshipConnection starshipConnection;
    private boolean[] keyStates, keyStatesChanged;

    public enum DisplayState{
        MENU,
        STARSHIP,
        PILOTING
    }
    private DisplayState displayState;
    private DisplayState nextState;
    
    private Timer timer;
    
    private TypingKeyAdapter typingKeyAdapter;
    
    public PilotingDisplay(StarshipConnection starshipConnection) {
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //this.setUndecorated(true);
        this.setSize(WIDTH, HEIGHT);
        this.add(new MenuPanel(this));
        this.pack();
        this.setVisible(true);
        this.createBufferStrategy(2);
        
        this.starshipConnection = starshipConnection;
        this.displayState = DisplayState.MENU;
        this.nextState = DisplayState.MENU;
        this.keyStates = new boolean[6];
        for(int i = 0; i < this.keyStates.length; i++) {
            this.keyStates[i] = false;
        }
        this.keyStatesChanged = new boolean[6];
        for(int i = 0; i < this.keyStatesChanged.length; i++) {
            this.keyStatesChanged[i] = false;
        }
        
        this.timer = new Timer(tickInterval, new Ticker(this));
        
        this.setFocusable(true);
        this.typingKeyAdapter = new TypingKeyAdapter();
        this.addKeyListener(typingKeyAdapter);
    }
    
    public TypingKeyAdapter getTypingKeyAdapter() {
        return this.typingKeyAdapter;
    }
    
    private void processInput() {
        
    }
    
    private void dispatchStarshipMessages() {
        if(this.starshipConnection.getConnectionStatus() == StarshipConnection.ConnectionStatus.CONNECTED) {
            // Work out if any states have changed
            boolean aKeyStateChanged = false;
            for(int i = 0; i < this.keyStatesChanged.length; i++) {
                aKeyStateChanged |= this.keyStatesChanged[i];
            }
            
            // If some state has changed, prepare the appropriate message
            // more clauses to go in this if statement, it is not redundant
            if(aKeyStateChanged) {
                STSup.Builder stsUp = STSup.newBuilder();
                if(aKeyStateChanged) {
                    STSup.Piloting.Builder stsUpPiloting = STSup.Piloting.newBuilder();
                    if(this.keyStatesChanged[0]) {
                        stsUpPiloting.setTurnAnti(this.keyStatesChanged[0]);
                        this.keyStatesChanged[0] = false;
                    }
                    if(this.keyStatesChanged[1]) {
                        stsUpPiloting.setTurnClock(this.keyStatesChanged[1]);
                        this.keyStatesChanged[1] = false;
                    }
                    if(this.keyStatesChanged[2]) {
                        stsUpPiloting.setThrustLeft(this.keyStatesChanged[2]);
                        this.keyStatesChanged[2] = false;
                    }
                    if(this.keyStatesChanged[3]) {
                        stsUpPiloting.setThrustRight(this.keyStatesChanged[3]);
                        this.keyStatesChanged[3] = false;
                    }
                    if(this.keyStatesChanged[4]) {
                        stsUpPiloting.setThrustForward(this.keyStatesChanged[4]);
                        this.keyStatesChanged[4] = false;
                    }
                    if(this.keyStatesChanged[5]) {
                        stsUpPiloting.setThrustBackward(this.keyStatesChanged[5]);
                        this.keyStatesChanged[5] = false;
                    }
                    stsUp.setPiloting(stsUpPiloting);
                }
                
                this.starshipConnection.sendMessageToStarship(stsUp.build());
            }
        }
    }
    
    private void drawAllTheThings() {
        BufferStrategy bs = this.getBufferStrategy();
        Graphics g = bs.getDrawGraphics();
        
        this.repaint();
        this.paintComponents(g);
        
        g.dispose();
    }

    
    public void tick() {
        this.repaint();
    }
    
    public void start() {
        this.timer.start();
    }
    
    public void stop() {
        this.timer.stop();
    }
    
    private class Ticker implements ActionListener {
        private PilotingDisplay disp;
        
        public Ticker(PilotingDisplay disp) {
            this.disp = disp;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            this.disp.tick();
        }
    }
}
