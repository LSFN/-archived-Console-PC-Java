package org.lsfn.console_pc.specialised_display;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.lsfn.console_pc.StarshipConnection;
import org.lsfn.console_pc.custom_consoles.ConsoleManager;
import org.lsfn.console_pc.data_store.DataStore;
import org.lsfn.console_pc.ship_designer.ShipDesigner;
import org.lsfn.console_pc.specialised_display.ISpecialisedDisplay.SpecialisedDisplays;

public class SpecialisedDisplayManager extends JPanel implements Runnable, HierarchyBoundsListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3595465800796113913L;

	private static final int tickInterval = 50;
	
	private SpecialisedDisplays currentDisplayName;
    private ISpecialisedDisplay currentDisplay;
    private JFrame parent;
    private StarshipConnection starshipConnection;
    private DataStore dataStore;
    
    private boolean running;
    
	
    public SpecialisedDisplayManager(JFrame parent, StarshipConnection starshipConnection, DataStore dataStore) {
    	this.starshipConnection = starshipConnection;
    	this.dataStore = dataStore;
    	this.currentDisplayName = SpecialisedDisplays.MENU;
    	
    	this.parent = parent;
    	this.parent.add(this);
    	this.parent.pack();
    	this.currentDisplay = new Menu(this.starshipConnection, this.dataStore, this.getBounds());
    	this.parent.addKeyListener(this.currentDisplay);
    	this.addMouseListener(this.currentDisplay);
    	this.addMouseMotionListener(this.currentDisplay);
    	this.addMouseWheelListener(this.currentDisplay);
    	
    	this.running = true;
    }
    
    public void switchSpecialisedDisplay() {
    	if(this.currentDisplayName != this.currentDisplay.nextDisplay()) {
    		switch(this.currentDisplay.nextDisplay()) {
    		case MENU:
    			this.currentDisplay = new Menu(this.starshipConnection, this.dataStore, this.getBounds());
    			break;
    		case LOBBY:
    			this.currentDisplay = new Lobby(this.starshipConnection, this.dataStore, this.getBounds());
    			break;
    		case CONSOLE:
    			this.currentDisplay = new ConsoleManager(this.starshipConnection, this.dataStore, this.getBounds());
    			break;
    		case SHIP_DESIGNER:
    			this.currentDisplay = new ShipDesigner(this.parent, this.getBounds());
    			break;
    		case EXIT:
    			break;
    		}
    		this.currentDisplayName = this.currentDisplay.nextDisplay();
    		this.parent.addKeyListener(this.currentDisplay);
        	this.addMouseListener(this.currentDisplay);
        	this.addMouseMotionListener(this.currentDisplay);
        	this.addMouseWheelListener(this.currentDisplay);
    	}
    }
    
	@Override
	public void run() {
		while(running) {
			switchSpecialisedDisplay();
			repaint();
			try {
				Thread.sleep(tickInterval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		this.currentDisplay.draw((Graphics2D)g);
	}

	@Override
	public void ancestorMoved(HierarchyEvent e) {
		
	}

	@Override
	public void ancestorResized(HierarchyEvent e) {
		this.currentDisplay.setBounds(getBounds());
	}

}
