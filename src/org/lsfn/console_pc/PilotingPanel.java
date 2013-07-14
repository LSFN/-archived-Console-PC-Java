package org.lsfn.console_pc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class PilotingPanel extends JPanel implements MouseListener, KeyListener {
    
    public PilotingPanel() {
        
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
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        paintCalibration(g);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1280, 1024);
    }

    /*
    private void dispatchStarshipMessages() {
        if(this.starshipConnection.getConnectionStatus() == StarshipConnection.ConnectionStatus.CONNECTED) {
            // Work out if any states have changed
            boolean aKeyStateChanged = false;
            for(int i = 0; i < this.keyStatesChanged.length; i++) {
                aKeyStateChanged |= this.keyStatesChanged[i];
            }
            
            // If some state has changed, prepare the appropriate message
            // more clauses to go in this if statement, it is not redundant
            if(aKeyStateChanged) {
                STSup.Builder stsUp = STSup.newBuilder();
                if(aKeyStateChanged) {
                    STSup.Piloting.Builder stsUpPiloting = STSup.Piloting.newBuilder();
                    if(this.keyStatesChanged[0]) {
                        stsUpPiloting.setTurnAnti(this.keyStatesChanged[0]);
                        this.keyStatesChanged[0] = false;
                    }
                    if(this.keyStatesChanged[1]) {
                        stsUpPiloting.setTurnClock(this.keyStatesChanged[1]);
                        this.keyStatesChanged[1] = false;
                    }
                    if(this.keyStatesChanged[2]) {
                        stsUpPiloting.setThrustLeft(this.keyStatesChanged[2]);
                        this.keyStatesChanged[2] = false;
                    }
                    if(this.keyStatesChanged[3]) {
                        stsUpPiloting.setThrustRight(this.keyStatesChanged[3]);
                        this.keyStatesChanged[3] = false;
                    }
                    if(this.keyStatesChanged[4]) {
                        stsUpPiloting.setThrustForward(this.keyStatesChanged[4]);
                        this.keyStatesChanged[4] = false;
                    }
                    if(this.keyStatesChanged[5]) {
                        stsUpPiloting.setThrustBackward(this.keyStatesChanged[5]);
                        this.keyStatesChanged[5] = false;
                    }
                    stsUp.setPiloting(stsUpPiloting);
                }
                
                this.starshipConnection.sendMessageToStarship(stsUp.build());
            }
        }
    }
    */
    
    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }
}
