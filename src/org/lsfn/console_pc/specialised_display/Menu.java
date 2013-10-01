package org.lsfn.console_pc.specialised_display;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;

import org.lsfn.console_pc.data_store.DataPath;
import org.lsfn.console_pc.data_store.DataStore;
import org.lsfn.console_pc.data_store.sinks.ISinkBoolean;
import org.lsfn.console_pc.data_store.sinks.ISinkInteger;
import org.lsfn.console_pc.data_store.sinks.ISinkString;
import org.lsfn.console_pc.data_store.sources.ISourceInteger;
import org.lsfn.console_pc.data_store.sources.ISourceString;
import org.lsfn.console_pc.data_store.sources.ISourceTrigger;

public class Menu implements ISpecialisedDisplay {

	private SpecialisedDisplays nextDisplay;
	
	private ISourceString hostnameSource;
	private ISourceInteger portSource;
	private ISinkString hostnameSink;
	private ISinkInteger portSink;
	private ISourceTrigger connectSource;
	private ISinkBoolean connectedSink;
	
	private enum SelectedElement {
		NONE,
		HOSTNAME,
		PORT
	}
	private SelectedElement selectedElement;
	
	private Rectangle bounds, menuBackgroundRect, hostnameRect, portRect, connectRect, shipDesignerRect, exitRect;
	
	public Menu(DataStore dataStore, Rectangle bounds) {
		this.nextDisplay = SpecialisedDisplays.MENU;
		this.hostnameSource = dataStore.findSourceString(new DataPath("starshipConnection/hostname"));
		this.portSource = dataStore.findSourceInteger(new DataPath("starshipConnection/port"));
		this.hostnameSink = dataStore.findSinkString(new DataPath("starshipConnection/hostname"));
		this.portSink = dataStore.findSinkInteger(new DataPath("starshipConnection/port"));
		this.connectSource = dataStore.findSourceTrigger(new DataPath("starshipConnection/connect"));
		this.connectedSink = dataStore.findSinkBoolean(new DataPath("starshipConnection/connected"));
		this.selectedElement = SelectedElement.NONE;
		setBounds(bounds);
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		int charValue = (int)arg0.getKeyChar();
		if(this.selectedElement == SelectedElement.HOSTNAME) {
	        if(charValue >= (int)' ' && charValue <= (int)'~') {
	            this.hostnameSource.appendCharacter(arg0.getKeyChar());
	        } else if(arg0.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
	        	this.hostnameSource.deleteCharacter();
	        }
		} else if(this.selectedElement == SelectedElement.PORT) {
			if(charValue >= (int)'0' && charValue <= (int)'9') {
                this.portSource.addTypedDigit(arg0.getKeyChar());
            } else if(arg0.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                this.portSource.deleteTypedDigit();
            }
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(this.hostnameRect.contains(arg0.getPoint())) {
			this.selectedElement = SelectedElement.HOSTNAME;
		} else if(this.portRect.contains(arg0.getPoint())) {
			this.selectedElement = SelectedElement.PORT;
		} else if(this.connectRect.contains(arg0.getPoint())) {
			this.selectedElement = SelectedElement.NONE;
			this.connectSource.trigger();
		} else if(this.shipDesignerRect.contains(arg0.getPoint())) {
			this.selectedElement = SelectedElement.NONE;
			this.nextDisplay = SpecialisedDisplays.SHIP_DESIGNER;
		} else if(this.exitRect.contains(arg0.getPoint())) {
			this.selectedElement = SelectedElement.NONE;
			this.nextDisplay = SpecialisedDisplays.EXIT;
		} else {
			this.selectedElement = SelectedElement.NONE;
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {

	}

	@Override
	public SpecialisedDisplays nextDisplay() {
		if(this.connectedSink.getData()) {
			this.nextDisplay = SpecialisedDisplays.LOBBY;
		}
		return this.nextDisplay;
	}

	private void drawTextWithCentre(Graphics2D g, String text, Point p) {
		Rectangle2D stringRect = g.getFontMetrics().getStringBounds(text, g);
        g.drawString(text, (int)(p.x - stringRect.getWidth()/2), (int)(p.y - stringRect.getHeight()/2));
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.black);
		g.fill(bounds);
		g.setColor(new Color(0, 0, 128));
		g.fill(menuBackgroundRect);
		g.setColor(Color.white);
		g.fill(hostnameRect);
		g.fill(portRect);
		g.setColor(Color.blue);
		g.fill(connectRect);
		g.fill(shipDesignerRect);
		g.fill(exitRect);
		g.setColor(Color.black);
		drawTextWithCentre(g, this.hostnameSink.getData(), new Point((int)hostnameRect.getCenterX(), (int)hostnameRect.getCenterY()));
		drawTextWithCentre(g, this.portSink.getData().toString(), new Point((int)portRect.getCenterX(), (int)portRect.getCenterY()));
		g.setColor(Color.white);
		drawTextWithCentre(g, "Connect", new Point((int)connectRect.getCenterX(), (int)connectRect.getCenterY()));
		drawTextWithCentre(g, "Ship Designer", new Point((int)shipDesignerRect.getCenterX(), (int)shipDesignerRect.getCenterY()));
		drawTextWithCentre(g, "Exit", new Point((int)exitRect.getCenterX(), (int)exitRect.getCenterY()));
	}


	@Override
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
		int width = (int)(bounds.width/5.0);
		int height = (int)(bounds.height/3.0);
		this.menuBackgroundRect = new Rectangle(width * 2, height, width, height);
		int height2 = (int)(height / 6.0);
		int height3 = (int)(height2 / 6.0);
		int width2 = (int)(width*9.0 / 10.0);
		int width3 = (int)(width / 20.0);
		this.hostnameRect = new Rectangle(this.menuBackgroundRect.x + width3, this.menuBackgroundRect.y + height3, width2, height2);
		this.portRect = new Rectangle(this.menuBackgroundRect.x + width3, this.menuBackgroundRect.y + height2 + 2*height3, width2, height2);
		this.connectRect = new Rectangle(this.menuBackgroundRect.x + width3, this.menuBackgroundRect.y + 2*height2 + 3*height3, width2, height2);
		this.shipDesignerRect = new Rectangle(this.menuBackgroundRect.x + width3, this.menuBackgroundRect.y + 3*height2 + 4*height3, width2, height2);
		this.exitRect = new Rectangle(this.menuBackgroundRect.x + width3, this.menuBackgroundRect.y + 4*height2 + 5*height3, width2, height2);
	}

}
