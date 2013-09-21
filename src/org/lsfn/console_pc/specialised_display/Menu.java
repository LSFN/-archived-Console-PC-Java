package org.lsfn.console_pc.specialised_display;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;

import org.lsfn.console_pc.StarshipConnection;
import org.lsfn.console_pc.data_store.DataPath;
import org.lsfn.console_pc.data_store.DataStore;
import org.lsfn.console_pc.data_store.sinks.ISinkInteger;
import org.lsfn.console_pc.data_store.sinks.ISinkString;
import org.lsfn.console_pc.data_store.sources.ISourceInteger;
import org.lsfn.console_pc.data_store.sources.ISourceString;

public class Menu implements ISpecialisedDisplay {

	private StarshipConnection starshipConnection;
	private DataStore dataStore;
	private SpecialisedDisplays nextDisplay;
	
	private ISourceString hostnameSource;
	private ISourceInteger portSource;
	private ISinkString hostnameSink;
	private ISinkInteger portSink;
	
	private enum SelectedElement {
		NONE,
		HOSTNAME,
		PORT
	}
	private SelectedElement selectedElement;
	
	private Rectangle bounds, menuBackgroundRect, hostnameRect, portRect, connectRect, shipDesignerRect, exitRect;
	
	public Menu(StarshipConnection starshipConnection, DataStore dataStore, Rectangle bounds) {
		this.starshipConnection = starshipConnection;
		this.dataStore = dataStore;
		this.nextDisplay = SpecialisedDisplays.MENU;
		this.hostnameSource = this.dataStore.findSourceString(new DataPath("starshipConnection/hostname"));
		this.portSource = this.dataStore.findSourceInteger(new DataPath("starshipConnection/port"));
		this.hostnameSink = this.dataStore.findSinkString(new DataPath("starshipConnection/hostname"));
		this.portSink = this.dataStore.findSinkInteger(new DataPath("starshipConnection/port"));
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
			if(this.starshipConnection.connect(this.hostnameSink.getData(), this.portSink.getData())) {
				this.nextDisplay = SpecialisedDisplays.LOBBY;
			}
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
		g.setColor(Color.blue);
		g.fill(hostnameRect);
		g.fill(portRect);
		g.fill(connectRect);
		g.fill(shipDesignerRect);
		g.fill(exitRect);
		g.setColor(Color.white);
		drawTextWithCentre(g, this.hostnameSink.getData(), new Point((int)hostnameRect.getCenterX(), (int)hostnameRect.getCenterY()));
		drawTextWithCentre(g, this.portSink.getData().toString(), new Point((int)portRect.getCenterX(), (int)portRect.getCenterY()));
		drawTextWithCentre(g, "Connect", new Point((int)connectRect.getCenterX(), (int)connectRect.getCenterY()));
		drawTextWithCentre(g, "Ship Designer", new Point((int)shipDesignerRect.getCenterX(), (int)shipDesignerRect.getCenterY()));
		drawTextWithCentre(g, "Exit", new Point((int)exitRect.getCenterX(), (int)exitRect.getCenterY()));
	}


	@Override
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
		int width = bounds.width;
		int height = bounds.height;
		this.menuBackgroundRect = new Rectangle((int)(width * 2.0 / 5.0), (int)(height / 3.0), (int)(width / 5.0), (int)(height / 3.0));
		height /= 6.0;
		int height2 = (int)(height / 6.0);
		width -= 10;
		this.hostnameRect = new Rectangle(this.menuBackgroundRect.x + width/2, this.menuBackgroundRect.height + height2, width, height);
		this.portRect = new Rectangle(this.menuBackgroundRect.x + width/2, this.menuBackgroundRect.height + height + 2*height2, width, height);
		this.connectRect = new Rectangle(this.menuBackgroundRect.x + width/2, this.menuBackgroundRect.height + 2*height + 3*height2, width, height);
		this.shipDesignerRect = new Rectangle(this.menuBackgroundRect.x + width/2, this.menuBackgroundRect.height + 3*height + 4*height2, width, height);
		this.exitRect = new Rectangle(this.menuBackgroundRect.x + width/2, this.menuBackgroundRect.height + 4*height + 5*height2, width, height);
	}

}
