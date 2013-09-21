package org.lsfn.console_pc.specialised_display;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;

import org.lsfn.console_pc.StarshipConnection;
import org.lsfn.console_pc.data_store.DataPath;
import org.lsfn.console_pc.data_store.DataStore;
import org.lsfn.console_pc.data_store.sinks.ISinkBoolean;
import org.lsfn.console_pc.data_store.sinks.ISinkInteger;
import org.lsfn.console_pc.data_store.sinks.ISinkString;
import org.lsfn.console_pc.data_store.sources.ISourceInteger;
import org.lsfn.console_pc.data_store.sources.ISourceString;
import org.lsfn.console_pc.data_store.sources.ISourceTrigger;

public class Lobby implements ISpecialisedDisplay {

	private JFrame parent;
	private StarshipConnection starshipConnection;
	private DataStore dataStore;
	private SpecialisedDisplays nextDisplay;
	
	private ISourceString nebulaHostnameSource;
	private ISourceInteger nebulaPortSource;
	private ISinkString nebulaHostnameSink;
	private ISinkInteger nebulaPortSink;
	private ISinkBoolean nebulaConnectedSink;
	private ISourceTrigger connectNebulaSource;
	private ISourceTrigger disconnectNebulaSource;
	
	private ISinkString shipNameSink;
	private ISinkString shipNameEditSink;
	private ISourceString shipNameEditSource;
	private ISourceTrigger shipNameChangeTriggerSource;
	private ISinkBoolean readySink;
	private ISourceTrigger readyUpTriggerSource;
	
	private enum SelectedElement {
		NONE,
		NEBULA_HOSTNAME,
		NEBULA_PORT,
		SHIP_NAME
	}
	private SelectedElement selectedElement;
	
	private Rectangle bounds, disconnectStarshipRect, nebulaHostnameRect, nebulaPortRect, nebulaConnectRect, nebulaBackgroundRect, disconnectNebulaRect,
			shipNameRect, shipNameButtonRect, readyIndicatorRect, readyButtonRect, shipNameListRect;
	
	public Lobby(StarshipConnection starshipConnection, DataStore dataStore, Rectangle bounds) {
		this.starshipConnection = starshipConnection;
		this.dataStore = dataStore;
		this.nextDisplay = SpecialisedDisplays.MENU;
		this.nebulaHostnameSource = dataStore.findSourceString(new DataPath("nebulaConnection/hostname"));
		this.nebulaPortSource = dataStore.findSourceInteger(new DataPath("nebulaConnection/port"));
		this.nebulaHostnameSink = dataStore.findSinkString(new DataPath("nebulaConnection/hostname"));
		this.nebulaPortSink = dataStore.findSinkInteger(new DataPath("nebulaConnection/port"));
		this.nebulaConnectedSink = dataStore.findSinkBoolean(new DataPath("nebulaConnection/connected"));
		this.connectNebulaSource = dataStore.findSourceTrigger(new DataPath("nebulaConnection/connectNebula"));
		this.disconnectNebulaSource = dataStore.findSourceTrigger(new DataPath("nebulaConnection/disconnectNebula"));
		
		this.shipNameSink = dataStore.findSinkString(new DataPath("lobby/recordedShipName"));
		this.shipNameEditSink = dataStore.findSinkString(new DataPath("lobby/editShipName"));
		this.shipNameEditSource = dataStore.findSourceString(new DataPath("lobby/editShipName"));
		this.shipNameChangeTriggerSource = dataStore.findSourceTrigger(new DataPath("lobby/changeShipName"));
		this.readySink = dataStore.findSinkBoolean(new DataPath("lobby/ready"));
		this.readyUpTriggerSource = dataStore.findSourceTrigger(new DataPath("lobby/readyUp"));
		setBounds(bounds);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		int charValue = (int)arg0.getKeyChar();
		if(this.selectedElement == SelectedElement.NEBULA_HOSTNAME) {
	        if(charValue >= (int)' ' && charValue <= (int)'~') {
	            this.nebulaHostnameSource.appendCharacter(arg0.getKeyChar());
	        } else if(arg0.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
	        	this.nebulaHostnameSource.deleteCharacter();
	        }
		} else if(this.selectedElement == SelectedElement.SHIP_NAME) {
	        if(charValue >= (int)' ' && charValue <= (int)'~') {
	            this.shipNameEditSource.appendCharacter(arg0.getKeyChar());
	        } else if(arg0.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
	        	this.shipNameEditSource.deleteCharacter();
	        }
		} else if(this.selectedElement == SelectedElement.NEBULA_PORT) {
			if(charValue >= (int)'0' && charValue <= (int)'9') {
                this.nebulaPortSource.addTypedDigit(arg0.getKeyChar());
            } else if(arg0.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                this.nebulaPortSource.deleteTypedDigit();
            }
		}
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
		if(disconnectStarshipRect.contains(arg0.getPoint())) {
			this.selectedElement = SelectedElement.NONE;
			
		} else if(disconnectNebulaRect.contains(arg0.getPoint())) {
			this.selectedElement = SelectedElement.NONE;
			
		} else if(shipNameButtonRect.contains(arg0.getPoint())) {
			this.selectedElement = SelectedElement.NONE;
			
		} else if(nebulaHostnameRect.contains(arg0.getPoint())) {
			this.selectedElement = SelectedElement.NEBULA_HOSTNAME;
		} else if(nebulaPortRect.contains(arg0.getPoint())) {
			this.selectedElement = SelectedElement.NEBULA_PORT;
		} else if(shipNameRect.contains(arg0.getPoint())) {
			this.selectedElement = SelectedElement.SHIP_NAME;
		} else {
			this.selectedElement = SelectedElement.NONE;
		}
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

	private void drawTextInRect(Graphics2D g, String text, Rectangle rect) {
		Rectangle2D stringRect = g.getFontMetrics().getStringBounds(text, g);
        g.drawString(text, (int)(rect.getCenterX() - stringRect.getWidth()/2), (int)(rect.getCenterY() - stringRect.getHeight()/2));
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.black);
		g.fill(bounds);
		g.setColor(Color.blue);
		g.fill(disconnectStarshipRect);
		g.setColor(Color.white);
		drawTextInRect(g, "Disconnect from Starship", disconnectStarshipRect);
		if(this.nebulaConnectedSink.getData()) {
			g.setColor(Color.gray);
			g.fill(nebulaBackgroundRect);
			g.setColor(Color.blue);
			g.fill(disconnectNebulaRect);
			g.fill(shipNameRect);
			g.fill(shipNameButtonRect);
			g.fill(readyIndicatorRect);
			g.fill(readyButtonRect);
			g.fill(shipNameListRect);
			g.setColor(Color.white);
			// TODO various texts to be printed here
		} else {
			g.setColor(Color.blue);
			g.fill(nebulaHostnameRect);
			g.fill(nebulaPortRect);
			g.fill(nebulaConnectRect);
			g.setColor(Color.white);
			drawTextInRect(g, nebulaHostnameSink.getData(), nebulaHostnameRect);
			drawTextInRect(g, nebulaPortSink.getData().toString(), nebulaPortRect);
			drawTextInRect(g, "Connect", nebulaConnectRect);
		}
	}

	@Override
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
		int spacing = 10;
		int starshipRectWidth = (int)((bounds.width - spacing) / 4.0) - spacing;
		int starshipRectHeight = (int)(bounds.height / 10.0);
		disconnectStarshipRect = new Rectangle(spacing, spacing, starshipRectWidth, starshipRectHeight);
		nebulaHostnameRect = new Rectangle(2*spacing + starshipRectWidth, spacing, starshipRectWidth, starshipRectHeight);
		nebulaPortRect = new Rectangle(3*spacing + 2*starshipRectWidth, spacing, starshipRectWidth, starshipRectHeight);
		nebulaConnectRect = new Rectangle(4*spacing + 3*starshipRectWidth, spacing, starshipRectWidth, starshipRectHeight);
		nebulaBackgroundRect = new Rectangle(spacing, 2*spacing + starshipRectHeight, bounds.width - 2*spacing, bounds.height - 3*spacing - starshipRectHeight);
		int x = nebulaBackgroundRect.x;
		int y = nebulaBackgroundRect.y;
		int width = (int)(((nebulaBackgroundRect.width - spacing) / 2.0) - spacing);
		int height = (int)(((nebulaBackgroundRect.height - spacing) / 5.0) - spacing);
		disconnectNebulaRect = new Rectangle(x + spacing, y + spacing, width, height);
		shipNameRect = new Rectangle(x + spacing, y + height + 2*spacing, width, height);
		shipNameButtonRect = new Rectangle(x + spacing, y + 2*height + 3*spacing, width, height);
		readyIndicatorRect = new Rectangle(x + spacing, y + 3*height + 4*spacing, width, height);
		readyButtonRect = new Rectangle(x + spacing, y + 4*height + 5*spacing, width, height);
		shipNameListRect = new Rectangle(x + width + 2*spacing, y + spacing, width, 5*height + 4*spacing);
	}

}
