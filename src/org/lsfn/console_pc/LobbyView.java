package org.lsfn.console_pc;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;

public class LobbyView implements View {

    private Rectangle disconnectStarshipButton, nebulaConnectedBox, disconnectNebulaButton,
        shipNameTextField, changeShipNameButton, readyIndicator, readyButton, shipsInGameList, 
        nebulaDisconnectedBox, hostTextField, portTextField, connectButton;

    private String shipNameText, hostText, portText, previousShipName;
    
    private DataManager dataManager;
    private ViewManager viewManager;
    
    private enum LobbyComponents {
        NONE,
        
        DISCONNECT_STARSHIP_BUTTON,
        DISCONNECT_NEBULA_BUTTON,
        SHIP_NAME_TEXT_FIELD,
        CHANGE_SHIP_NAME_BUTTON,
        READY_INDICATOR,
        READY_BUTTON,
        SHIPS_IN_GAME_LIST,
        
        HOST_TEXT_FIELD,
        PORT_TEXT_FIELD,
        CONNECT_BUTTON
    }
    private LobbyComponents lastComponentClicked;
    
    public LobbyView(ViewManager viewManager, DataManager dataManager) {
        this.dataManager = dataManager;
        this.viewManager = viewManager;
        
        this.hostText = "localhost";
        this.portText = "39461";
        this.shipNameText = "Mungle Box";
        this.previousShipName = "Mungle Box";
        
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
    
    @Override
    public void drawView(Graphics2D g, Rectangle bounds) {
        clearPanel(g, bounds);
        paintDisconnectStarshipButton(g);
        if(this.dataManager.isConnectionConnected()) {
            paintStarshipConnectedBox(g);
        } else {
            paintStarshipDisconnectedBox(g);
        }
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
        if(!this.dataManager.getLobbyShipName().equals(this.previousShipName)) {
            this.previousShipName = this.dataManager.getLobbyShipName();
            this.shipNameText = this.dataManager.getLobbyShipName();
        }
        g2d.setColor(Color.WHITE);
        g2d.drawString(shipNameText, shipNameTextField.x + 10, shipNameTextField.y + 10);
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
        if(this.dataManager.getLobbyReadyState()) {
            g2d.setColor(Color.DARK_GRAY);
        } else {
            g2d.setColor(Color.RED);
        }
        g2d.fillOval(readyIndicator.x + 5, readyIndicator.y + 5, 20, 20);
        if(this.dataManager.getLobbyReadyState()) {
            g2d.setColor(Color.GREEN);
        } else {
            g2d.setColor(Color.DARK_GRAY);
        }
        g2d.fillOval(readyIndicator.x + 35, readyIndicator.y + 5, 20, 20);
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
        List<String> shipsInGame = this.dataManager.getLobbyShipsInGame();
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
        g2d.fill(nebulaDisconnectedBox);
        paintHostTextField(g2d);
        paintPortTextField(g2d);
        paintConnectButton(g2d);
    }
    
    private void clearPanel(Graphics2D g, Rectangle bounds) {
        g.setColor(Color.WHITE);
        g.fill(bounds);
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        char character = e.getKeyChar();
        int code = e.getKeyCode();
        
        if(this.lastComponentClicked == LobbyComponents.SHIP_NAME_TEXT_FIELD) {
            // The range of characters from ' ' through to '~' is the set of printable ascii characters 
            if((int)character >= (int)' ' && (int)character <= (int)'~') {
                this.shipNameText = this.shipNameText + character;
            } else if(code == KeyEvent.VK_BACK_SPACE) {
                if(this.shipNameText.length() > 0) {
                    this.shipNameText = this.shipNameText.substring(0, this.shipNameText.length() - 1);
                }
            }
        } else if (this.lastComponentClicked == LobbyComponents.HOST_TEXT_FIELD) {
            // The character space of ' ' through '~' is the set of printable characters 
            if((int)character >= (int)' ' && (int)character <= (int)'~') {
                this.hostText = this.hostText + character;
            } else if(code == KeyEvent.VK_BACK_SPACE) {
                if(this.hostText.length() > 0) {
                    this.hostText = this.hostText.substring(0, this.hostText.length() - 1);
                }
            }
        } else if(this.lastComponentClicked == LobbyComponents.PORT_TEXT_FIELD) {
            // This covers just the decimal digits
            if((int)character >= (int)'0' && (int)character <= (int)'9') {
                this.portText = this.portText + character;
            } else if(code == KeyEvent.VK_BACK_SPACE) {
                if(this.portText.length() > 0) {
                    this.portText = this.portText.substring(0, this.portText.length() - 1);
                }
            }
        }

    }
    
    @Override
    public void keyPressed(KeyEvent arg0) {

    }

    @Override
    public void keyReleased(KeyEvent arg0) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(this.dataManager.isConnectionConnected()) {
            if(this.disconnectNebulaButton.contains(e.getPoint())) {
                this.lastComponentClicked = LobbyComponents.DISCONNECT_NEBULA_BUTTON;
                this.dataManager.disconnectFromNebula();
            } else if(this.shipNameTextField.contains(e.getPoint())) {
                this.lastComponentClicked = LobbyComponents.SHIP_NAME_TEXT_FIELD;
            } else if(this.changeShipNameButton.contains(e.getPoint())) {
                this.lastComponentClicked = LobbyComponents.CHANGE_SHIP_NAME_BUTTON;
                this.dataManager.setDesiredShipName(shipNameText);
            } else if(this.readyIndicator.contains(e.getPoint())) {
                this.lastComponentClicked = LobbyComponents.READY_INDICATOR;
            } else if(this.readyButton.contains(e.getPoint())) {
                this.lastComponentClicked = LobbyComponents.READY_BUTTON;
                this.dataManager.setDesiredReadyState(!this.dataManager.getLobbyReadyState());
            } else if(this.shipsInGameList.contains(e.getPoint())) {
                this.lastComponentClicked = LobbyComponents.SHIPS_IN_GAME_LIST;
            } else {
                this.lastComponentClicked = LobbyComponents.NONE;
            }
        } else {
            if(this.hostTextField.contains(e.getPoint())) {
                this.lastComponentClicked = LobbyComponents.HOST_TEXT_FIELD;
            } else if(this.portTextField.contains(e.getPoint())) {
                this.lastComponentClicked = LobbyComponents.PORT_TEXT_FIELD;
            } else if(this.connectButton.contains(e.getPoint())) {
                this.lastComponentClicked = LobbyComponents.CONNECT_BUTTON;
                this.dataManager.connectToNebula(hostText, Integer.parseInt(portText));
            } else {
                this.lastComponentClicked = LobbyComponents.NONE;
            }
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
}
