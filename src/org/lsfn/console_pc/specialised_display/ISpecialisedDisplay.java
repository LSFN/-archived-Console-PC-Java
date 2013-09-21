package org.lsfn.console_pc.specialised_display;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

public interface ISpecialisedDisplay extends KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
    
	public enum SpecialisedDisplays {
    	MENU,
    	LOBBY,
    	CONSOLE,
    	SHIP_DESIGNER,
    	EXIT
    }
	public SpecialisedDisplays nextDisplay();
	public void draw(Graphics2D g);
	public void setBounds(Rectangle bounds);
}
