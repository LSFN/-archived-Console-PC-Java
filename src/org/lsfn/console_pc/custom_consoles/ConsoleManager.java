package org.lsfn.console_pc.custom_consoles;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.HierarchyEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JFrame;

import org.lsfn.console_pc.StarshipConnection;
import org.lsfn.console_pc.custom_consoles.bindings.BindingManager;
import org.lsfn.console_pc.custom_consoles.input.InputManager;
import org.lsfn.console_pc.custom_consoles.screen.ScreenManager;
import org.lsfn.console_pc.custom_consoles.widgets.WidgetManager;
import org.lsfn.console_pc.data_store.DataStore;
import org.lsfn.console_pc.specialised_display.ISpecialisedDisplay;

public class ConsoleManager implements ISpecialisedDisplay {

    private StarshipConnection starshipConnection;
    private DataStore dataStore;
    private ScreenManager screenManager;
    private WidgetManager widgetManager;
    private BindingManager bindingManager;
    private InputManager inputManager;
    
    public ConsoleManager(StarshipConnection starshipConnection, DataStore dataStore, Rectangle bounds) {
        this.starshipConnection = starshipConnection;
        this.dataStore = dataStore;
        this.screenManager = new ScreenManager(this.dataStore);
        this.widgetManager = new WidgetManager(this.dataStore);
        this.bindingManager = new BindingManager(this.dataStore);
        this.inputManager = new InputManager(this.widgetManager, this.bindingManager);
        this.screenManager.addMouseListener(this.inputManager);
        setBounds(bounds);
    }

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SpecialisedDisplays nextDisplay() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBounds(Rectangle bounds) {
		// TODO Auto-generated method stub
		
	}
}
