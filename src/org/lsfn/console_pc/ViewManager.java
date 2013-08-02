package org.lsfn.console_pc;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class ViewManager extends JPanel implements Runnable, KeyListener {

    private static final Integer tickInterval = 50;
    
    private Thread viewThread;
    private DataManager dataManager;
    private View view;
    private boolean running;
    
    public enum ViewState {
        MENU,
        SHIP_DESIGNER,
        LOBBY,
        PILOTING
    }
    private ViewState currentView;
    private ViewState nextView;
    
    
    public ViewManager() {
        this.viewThread = new Thread(this);
        this.dataManager = new DataManager();
        this.currentView = ViewState.MENU;
        this.nextView = ViewState.MENU;
        this.view = new MenuView(this.dataManager);
        this.addMouseListener(this.view);
    }
    
    public void start() {
        this.viewThread.start();
    }
    
    public void changeView(ViewState nextView) {
        this.nextView = nextView;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        this.view.drawView((Graphics2D)g, this.getBounds());
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1280, 1024);
    }
    
    private void changeToMenu() {
        this.removeMouseListener(this.view);
        this.view = new MenuView(this.dataManager);
        this.addMouseListener(this.view);
        this.currentView = ViewState.MENU;
    }
    
    private void changeToShipDesigner() {
        this.removeMouseListener(this.view);
        this.view = new ShipDesignerView(this, this.dataManager);
        this.addMouseListener(this.view);
        this.currentView = ViewState.SHIP_DESIGNER;
    }
    
    private void changeToLobby() {
        this.removeMouseListener(this.view);
        this.view = new LobbyView(this.dataManager);
        this.addMouseListener(this.view);
        this.currentView = ViewState.LOBBY;
    }
    
    private void changeToPiloting() {
        this.removeMouseListener(this.view);
        this.view = new PilotingView(this.dataManager);
        this.addMouseListener(this.view);
        this.currentView = ViewState.PILOTING;
    }
    
    private void viewChange() {
        if(this.currentView == ViewState.MENU) {
            if(this.dataManager.isConnectedToStarship()) {
                changeToLobby();
            } else if(this.dataManager.getEnteredShipDesigner()) {
                changeToShipDesigner();
            }
        } else if(this.currentView == ViewState.SHIP_DESIGNER) {
            if(!this.dataManager.getEnteredShipDesigner()) {
                changeToMenu();
            }
        } else if(this.currentView == ViewState.LOBBY) {
            if(!this.dataManager.isConnectedToStarship()) {
                changeToMenu();
            } else if(this.dataManager.isLobbyGameStarted()) {
                changeToPiloting();
            }
        }
        // TODO method of leaving piloting view
    }
    
    @Override
    public void run() {
        this.running = true;
        while(this.running) {
            // Change the view if necessary
            viewChange();
            // Process the input from the Starship
            this.dataManager.processInput();
            // Repaint will cause paintComponent to be called at some convenient point in the future
            // This, in turn, will paint the current view
            repaint();
            // Generate and send output to the Starship
            this.dataManager.generateOutput();
            
            try {
                Thread.sleep(tickInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.view.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.view.keyReleased(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        this.view.keyTyped(e);
    }
    
    
}
