package org.lsfn.console_pc;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.util.List;

import javax.swing.Timer;
import javax.swing.JFrame;

import org.lsfn.console_pc.STS.STSdown;
import org.lsfn.console_pc.STS.STSup;

public class PilotingDisplay extends JFrame {
    
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 1024;
    private static final int tickInterval = 50;
    
    private ConsolePC consolePC;
    private StarshipConnection starshipConnection;
    private boolean[] keyStates, keyStatesChanged;

    public enum DisplayState{
        MENU,
        LOBBY,
        PILOTING
    }
    private DisplayState displayState;
    private DisplayState nextState;
    private MenuPanel menuPanel;
    private LobbyPanel lobbyPanel;
    private PilotingPanel pilotingPanel;
    
    private Timer timer;
    
    public PilotingDisplay(ConsolePC consolePC, StarshipConnection starshipConnection) {
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setFocusable(true);
        //this.setUndecorated(true);
        this.setSize(WIDTH, HEIGHT);
        this.setVisible(true);
        this.createBufferStrategy(2);
        
        this.pilotingPanel = null;
        this.lobbyPanel = null;
        this.menuPanel = new MenuPanel(this);
        this.add(this.menuPanel);
        this.addKeyListener(menuPanel);
        this.pack();
        
        this.consolePC = consolePC;
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
    }
    
    public void changeDisplayState(DisplayState state) {
        this.nextState = state;
    }
    
    private void displayStateChange() {
        if(this.nextState != this.displayState) {
            if(this.displayState == DisplayState.MENU) {
                this.remove(menuPanel);
                this.removeKeyListener(menuPanel);
                this.menuPanel = null;
            } else if(this.displayState == DisplayState.LOBBY) {
                this.remove(lobbyPanel);
                this.removeKeyListener(lobbyPanel);
                this.lobbyPanel = null;
            } else if(this.displayState == DisplayState.PILOTING) {
                this.remove(pilotingPanel);
                this.removeKeyListener(pilotingPanel);
                this.pilotingPanel = null;
            }
            
            if(this.nextState == DisplayState.MENU) {
                this.menuPanel = new MenuPanel(this);
                this.add(this.menuPanel);
                this.addKeyListener(menuPanel);
            } else if(this.nextState == DisplayState.LOBBY) {
                this.lobbyPanel = new LobbyPanel(this);
                this.add(this.lobbyPanel);
                this.addKeyListener(lobbyPanel);
            } else if(this.nextState == DisplayState.PILOTING) {
                this.pilotingPanel = new PilotingPanel();
                this.add(this.pilotingPanel);
                this.addKeyListener(this.pilotingPanel);
            }
            this.pack();
            
            this.displayState = this.nextState;
        }
    }
    
    private void processInput() {
        List<STSdown> messages = this.starshipConnection.receiveMessagesFromStarship();
        for(STSdown message : messages) {
            if(this.displayState == DisplayState.LOBBY) {
                if(message.hasConnection()) {
                    this.lobbyPanel.processConnection(message.getConnection());
                }
                if(message.hasLobby()) {
                    this.lobbyPanel.processLobby(message.getLobby());
                }
            }
            
        }
    }
    
    public void tick() {
        displayStateChange();
        this.repaint();
    }
    
    public void start() {
        this.timer.start();
    }
    
    public void stop() {
        this.timer.stop();
    }
    
    // Horrible functionality exposure hack
    // TODO make ConsolePC the JFrame
    public ConsolePC getConsolePC() {
        return this.consolePC;
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
