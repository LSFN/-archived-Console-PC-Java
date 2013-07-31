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
    
    public enum ViewState {
        MENU,
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
        this.view = new MenuView(this, this.dataManager);
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
    
    private void viewChange() {
        if(this.currentView != this.nextView) {
            if(this.nextView == ViewState.MENU) {
                this.view = new MenuView(this, this.dataManager);
            } else if(this.nextView == ViewState.LOBBY) {
                this.view = new LobbyView(this.dataManager);
            } else if(this.nextView == ViewState.LOBBY) {
                this.view = new PilotingView(this.dataManager);
            }
        }
    }
    
    @Override
    public void run() {
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
