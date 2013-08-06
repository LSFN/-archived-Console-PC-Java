package org.lsfn.console_pc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

public class ScreenManager extends JPanel implements Runnable, KeyListener, HierarchyBoundsListener {

    private static final Integer tickInterval = 50;
    
    private Thread viewThread;
    private DataManager dataManager;
    private boolean running;

    private Screen screen;
    private Map<String, Screen> screens;
    
    public ScreenManager() {
        this.viewThread = new Thread(this);
        this.dataManager = new DataManager();
        this.addHierarchyBoundsListener(this);
        
        this.screens = new HashMap<String, Screen>();
        File screenFolder = new File("screens/");
        String[] screenFiles = screenFolder.list(new FilenameFilter() {
            
            @Override
            public boolean accept(File arg0, String arg1) {
                return arg1.endsWith(".screen");
            }
        });
        for(int i = 0; i < screenFiles.length; i++) {
            System.out.println("File name: " + screenFiles[i]);
            Screen loadedScreen = Screen.loadScreen("screens/" + screenFiles[i]);
            if(loadedScreen != null) {
                System.out.println("Loaded screen " + loadedScreen.getScreenName());
                this.screens.put(loadedScreen.getScreenName(), loadedScreen);
            }
        }
        
        this.screen = this.screens.get("Menu");
        if(this.screen != null) {
            this.screen.setBounds(getBounds());
        }
    }
    
    public void start() {
        this.viewThread.start();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2d = (Graphics2D)g;
        if(this.screen == null) {
            g2d.setColor(Color.BLACK);
            g2d.fill(getBounds());
            g2d.setColor(Color.RED);
            g2d.drawString("ERROR", getBounds().width/2, getBounds().height/2);
        } else {
            this.screen.drawScreen(g2d);
        }
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1280, 1024);
    }
    
    private void viewChange() {
        
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
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void ancestorMoved(HierarchyEvent e) {
    }

    @Override
    public void ancestorResized(HierarchyEvent e) {
        // recalculate the positions of the widgets
        if(this.screen != null) {
            this.screen.setBounds(getBounds());
        }
    }
    
    
}
