package org.lsfn.console_pc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.lsfn.console_pc.StarshipConnection.ConnectionStatus;

public class LobbyPanel extends JPanel implements MouseListener, KeyListener {

    private Rectangle disconnectStarshipButton, nebulaConnectedBox, disconnectNebulaButton,
            shipNameTextField, changeShipNameButton, readyIndicator, readyButton, shipsInGameList, 
            nebulaDisconnectedBox, hostTextField, portTextField, connectButton;
    
    private String shipNameText, hostText, portText;
    private boolean connectedToNebula, ready;
    private List<String> shipsInGame;
    
    private PilotingDisplay pilotingDisplay;
    
    public LobbyPanel(PilotingDisplay pilotingDisplay) {
        this.pilotingDisplay = pilotingDisplay;
        
        this.shipNameText = "Mungle Box";
        this.hostText = "localhost";
        this.portText = "39461";
        this.connectedToNebula = false;
        this.ready = false;
        this.shipsInGame = new ArrayList<String>();
        
        this.disconnectStarshipButton = new Rectangle(10, 10, 200, 30);
        
        this.nebulaConnectedBox = new Rectangle(220, 10, 220, 500);
        this.disconnectNebulaButton = new Rectangle(230, 20, 200, 30);
        this.shipNameTextField = new Rectangle(230, 60, 200, 30);
        this.changeShipNameButton = new Rectangle(230, 100, 200, 30);
        this.readyIndicator = new Rectangle(230, 140, 200, 30);
        this.readyButton = new Rectangle(230, 180, 200, 30);
        this.shipsInGameList = new Rectangle(230, 220, 200, 200);
        
        this.nebulaDisconnectedBox = new Rectangle(220, 10, 220, 500);
        this.hostTextField = new Rectangle(230, 20, 200, 30);
        this.portTextField = new Rectangle(230, 60, 200, 30);
        this.connectButton = new Rectangle(230, 100, 200, 30);
    }
    
    private void paintCalibration(Graphics g) {
        int squareSize = 10;
        Rectangle bounds = this.getBounds();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, squareSize, squareSize);
        g.fillRect(bounds.width - squareSize - 1, 0, squareSize, squareSize);
        g.fillRect(0, bounds.height - squareSize - 1, squareSize, squareSize);
        g.fillRect(bounds.width - squareSize - 1, bounds.height - squareSize - 1, squareSize, squareSize);
        g.setColor(Color.RED);
        g.drawRect(0, 0, squareSize, squareSize);
        g.drawRect(bounds.width - squareSize - 1, 0, squareSize, squareSize);
        g.drawRect(0, bounds.height - squareSize - 1, squareSize, squareSize);
        g.drawRect(bounds.width - squareSize - 1, bounds.height - squareSize - 1, squareSize, squareSize);
    }
    
    private void paintDisconnectStarshipButton(Graphics2D g2d) {
        g2d.setColor(Color.BLUE);
        g2d.fill(disconnectStarshipButton);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Disconnect from Starship", disconnectStarshipButton.x + 10, disconnectStarshipButton.y + 10);
    }
    
    private void paintDisconnectNebulaButton(Graphics2D g2d) {
        g2d.setColor(Color.BLUE);
        g2d.fill(disconnectNebulaButton);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Disconnect from Nebula", disconnectNebulaButton.x + 10, disconnectNebulaButton.y + 10);
    }
    
    private void paintShipNameTextField(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.fill(shipNameTextField);
        g2d.setColor(Color.WHITE);
        g2d.drawString(this.shipNameText, shipNameTextField.x + 10, shipNameTextField.y + 10);
    }
    
    private void paintChangeShipNameButton(Graphics2D g2d) {
        g2d.setColor(Color.BLUE);
        g2d.fill(changeShipNameButton);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Change ship name", changeShipNameButton.x + 10, changeShipNameButton.y + 10);
    }
    
    private void paintReadyIndicator(Graphics2D g2d) {
        g2d.setColor(Color.GRAY);
        g2d.fill(readyIndicator);
        if(ready) {
            g2d.setColor(Color.DARK_GRAY);
        } else {
            g2d.setColor(Color.RED);
        }
        g2d.fillOval(readyIndicator.x + 10, readyIndicator.y + 10, 20, 20);
        if(ready) {
            g2d.setColor(Color.GREEN);
        } else {
            g2d.setColor(Color.DARK_GRAY);
        }
        g2d.fillOval(readyIndicator.x + 40, readyIndicator.y + 10, 20, 20);
    }
    
    private void paintReadyButton(Graphics2D g2d) {
        g2d.setColor(Color.BLUE);
        g2d.fill(readyButton);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Ready", readyButton.x + 10, readyButton.y + 10);
    }
    
    private void paintShipsInGameList(Graphics2D g2d) {
        g2d.setColor(Color.CYAN);
        g2d.fill(shipsInGameList);
        g2d.setColor(Color.BLACK);
        for(int i = 0; i < shipsInGame.size(); i++) {
            g2d.drawString(shipsInGame.get(i), shipsInGameList.x + 10, shipsInGameList.y + 10 + (i * 15));
        }
    }
    
    private void paintStarshipConnectedBox(Graphics2D g2d) {
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fill(nebulaConnectedBox);
        paintDisconnectNebulaButton(g2d);
        paintShipNameTextField(g2d);
        paintChangeShipNameButton(g2d);
        paintReadyIndicator(g2d);
        paintReadyButton(g2d);
        paintShipsInGameList(g2d);
    }
    
    private void paintHostTextField(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.fill(hostTextField);
        g2d.setColor(Color.WHITE);
        g2d.drawString(this.hostText, hostTextField.x + 10, hostTextField.y + 10);
    }
    
    private void paintPortTextField(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.fill(portTextField);
        g2d.setColor(Color.WHITE);
        g2d.drawString(this.portText, portTextField.x + 10, portTextField.y + 10);
    }
    
    private void paintConnectButton(Graphics2D g2d) {
        g2d.setColor(Color.BLUE);
        g2d.fill(connectButton);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Connect", connectButton.x + 10, connectButton.y + 10);
    }
    
    private void paintStarshipDisconnectedBox(Graphics2D g2d) {
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fill(nebulaConnectedBox);
        paintHostTextField(g2d);
        paintPortTextField(g2d);
        paintConnectButton(g2d);
    }
    
    private void paintElements(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        paintDisconnectStarshipButton(g2d);
        if(this.connectedToNebula) {
            paintStarshipConnectedBox(g2d);
        } else {
            paintStarshipDisconnectedBox(g2d);
        }
    }
    
    private void clearPanel(Graphics g) {
        Rectangle bounds = this.getBounds();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.fill(bounds);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        clearPanel(g);
        //paintCalibration(g);
        paintElements(g);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1280, 1024);
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        
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
    
}
