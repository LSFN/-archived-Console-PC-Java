package org.lsfn.console_pc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;

import org.lsfn.console_pc.STS.STSdown;
import org.lsfn.console_pc.ViewManager.ViewState;

public class MenuView implements View {
    
    private String hostText, portText;
    private Rectangle hostTextField, portTextField, connectButton, exitButton;
    private DataManager dataManager;
    private ViewManager viewManager;
    
    private enum MenuComponents {
        NONE,
        HOST_TEXT_FIELD,
        PORT_TEXT_FIELD,
        CONNECT_BUTTON,
        EXIT_BUTTON        
    }
    private MenuComponents lastComponentClicked;
    
    public MenuView(ViewManager viewManager, DataManager dataManager) {
        this.dataManager = dataManager;
        this.viewManager = viewManager;
        this.lastComponentClicked = MenuComponents.NONE;
        this.hostText = "localhost";
        this.portText = "39460";
        
        this.hostTextField = new Rectangle(10, 10, 100, 30);
        this.portTextField = new Rectangle(120, 10, 100, 30);
        this.connectButton = new Rectangle(10, 50, 100, 30);
        this.exitButton = new Rectangle(120, 50, 100, 30);
    }
    
    @Override
    public void drawView(Graphics2D g, Rectangle bounds) {
        clearPanel(g, bounds);
        paintCalibration(g, bounds);
        g.setColor(Color.BLACK);
        g.fill(hostTextField);
        g.fill(portTextField);
        g.setColor(Color.BLUE);
        g.fill(connectButton);
        g.fill(exitButton);
        g.setColor(Color.WHITE);
        g.drawString(hostText, this.hostTextField.x + 10, this.hostTextField.y + 20);
        g.drawString(portText, this.portTextField.x + 10, this.portTextField.y + 20);
        g.drawString("connect", this.connectButton.x + 10, this.connectButton.y + 20);
        g.drawString("exit", this.exitButton.x + 10, this.exitButton.y + 20);
    }

    private void clearPanel(Graphics2D g, Rectangle bounds) {
        g.setColor(Color.WHITE);
        g.fill(bounds);
    }
    
    private void paintCalibration(Graphics2D g, Rectangle bounds) {
        int squareSize = 10;
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
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if(this.hostTextField.contains(e.getPoint())) {
            this.lastComponentClicked = MenuComponents.HOST_TEXT_FIELD;
        } else if(this.portTextField.contains(e.getPoint())) {
            this.lastComponentClicked = MenuComponents.PORT_TEXT_FIELD;
        } else if(this.connectButton.contains(e.getPoint())) {
            System.out.println("Connect button clicked");
            this.lastComponentClicked = MenuComponents.CONNECT_BUTTON;
            if(this.dataManager.connectToStarship(this.hostText, Integer.valueOf(this.portText))) {
                this.viewManager.changeView(ViewState.LOBBY);
            }
        } else if(this.exitButton.contains(e.getPoint())) {
            System.out.println("exit button clicked");
            this.lastComponentClicked = MenuComponents.EXIT_BUTTON;
        } else {
            this.lastComponentClicked = MenuComponents.NONE;
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
    public void keyPressed(KeyEvent arg0) {
        
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char character = e.getKeyChar();
        int code = e.getKeyCode();
        
        if(this.lastComponentClicked == MenuComponents.HOST_TEXT_FIELD) {
            // The character space of ' ' through '~' is the set of printable characters 
            if((int)character >= (int)' ' && (int)character <= (int)'~') {
                this.hostText = this.hostText + character;
            } else if(code == KeyEvent.VK_BACK_SPACE) {
                if(this.hostText.length() > 0) {
                    this.hostText = this.hostText.substring(0, this.hostText.length() - 1);
                }
            }
        } else if(this.lastComponentClicked == MenuComponents.PORT_TEXT_FIELD) {
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

}
