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

import javax.swing.JPanel;

public class MenuPanel extends JPanel implements MouseListener, KeyListener {
    
    private PilotingDisplay pilotingDisplay;
    private String hostText, portText;
    private Rectangle hostTextField, portTextField, connectButton, exitButton;
    
    private enum MenuPanelComponents {
        NONE,
        HOST_TEXT_FIELD,
        PORT_TEXT_FIELD,
        CONNECT_BUTTON,
        EXIT_BUTTON        
    }
    private MenuPanelComponents lastComponentClicked;
    
    public MenuPanel(PilotingDisplay pilotingDisplay) {
        this.hostText = "localhost";
        this.portText = "39460";
        this.pilotingDisplay = pilotingDisplay;
        this.lastComponentClicked = MenuPanelComponents.NONE;
        
        this.hostTextField = new Rectangle(10, 10, 100, 30);
        this.portTextField = new Rectangle(120, 10, 100, 30);
        this.connectButton = new Rectangle(10, 50, 100, 30);
        this.exitButton = new Rectangle(120, 50, 100, 30);
        
        this.addMouseListener(this);
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
    
    private void paintElements(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fill(hostTextField);
        g2d.fill(portTextField);
        g2d.fill(connectButton);
        g2d.fill(exitButton);
        g2d.setColor(Color.WHITE);
        g.drawString(this.hostText, this.hostTextField.x + 10, this.hostTextField.y + 10);
        g.drawString(this.portText, this.portTextField.x + 10, this.portTextField.y + 10);
        g.drawString("Connect", this.connectButton.x + 10, this.connectButton.y + 10);
        g.drawString("Exit", this.exitButton.x + 10, this.exitButton.y + 10);
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
    public void keyPressed(KeyEvent e) {
        char character = e.getKeyChar();
        int code = e.getKeyCode();
        
        if(this.lastComponentClicked == MenuPanelComponents.HOST_TEXT_FIELD) {
            // The character space of ' ' through '~' is the set of printable characters 
            if((int)character >= (int)' ' && (int)character <= (int)'~') {
                this.hostText = this.hostText + character;
            } else if(code == KeyEvent.VK_BACK_SPACE) {
                if(this.hostText.length() > 0) {
                    this.hostText = this.hostText.substring(0, this.hostText.length() - 1);
                }
            }
        } else if(this.lastComponentClicked == MenuPanelComponents.PORT_TEXT_FIELD) {
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
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(this.hostTextField.contains(e.getPoint())) {
            this.lastComponentClicked = MenuPanelComponents.HOST_TEXT_FIELD;
        } else if(this.portTextField.contains(e.getPoint())) {
            this.lastComponentClicked = MenuPanelComponents.PORT_TEXT_FIELD;
        } else if(this.connectButton.contains(e.getPoint())) {
            System.out.println("Connect button clicked");
            this.lastComponentClicked = MenuPanelComponents.CONNECT_BUTTON;
            this.pilotingDisplay.getConsolePC().startStarshipClient(this.hostText, Integer.valueOf(this.portText));
        } else if(this.exitButton.contains(e.getPoint())) {
            System.out.println("exit button clicked");
            this.lastComponentClicked = MenuPanelComponents.EXIT_BUTTON;
            this.pilotingDisplay.getConsolePC().stopDisplay();
        } else {
            this.lastComponentClicked = MenuPanelComponents.NONE;
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
