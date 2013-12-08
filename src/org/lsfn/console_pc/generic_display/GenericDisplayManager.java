package org.lsfn.console_pc.generic_display;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.lsfn.console_pc.generic_data_storage.IDataItem;

public class GenericDisplayManager extends JPanel implements Runnable, HierarchyBoundsListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6226634844926372440L;
	private static final int tickInterval = 50;
	
    private JFrame parent;
    private boolean running;
    private IDataItem rootDataItem;
    private String currentDisplayName;
	
    public GenericDisplayManager(JFrame parent, IDataItem rootDataItem) {
    	this.parent = parent;
    	this.running = false;
    	this.rootDataItem = rootDataItem;
    	this.currentDisplayName = "";
    }
    
    private void switchDisplayIfNecessary() {
    	if(!this.currentDisplayName.equals(rootDataItem.toMap().get("genericDisplay").toMap().get("currentDisplay"))) {
    		// Load Display
    	}
    }
    
	@Override
	public void run() {
		this.running = true;
		while(running) {
			
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
		
	}

	@Override
	public void ancestorMoved(HierarchyEvent e) {
		
	}

	@Override
	public void ancestorResized(HierarchyEvent e) {
		
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(1024, 768);
	}

}
