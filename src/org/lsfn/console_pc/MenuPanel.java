package org.lsfn.console_pc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class MenuPanel extends JPanel {
    
    private PilotingDisplay pilotingDisplay;
    private String text;
    
    public MenuPanel(PilotingDisplay pilotingDisplay) {
        this.text = "";
        this.pilotingDisplay = pilotingDisplay;
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
    
    private void paintText(Graphics g) {
        this.text += this.pilotingDisplay.getTypingKeyAdapter().getBuffer();
        g.setColor(Color.BLACK);
        g.drawString(text, 10, 20);
        
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        paintCalibration(g);
        paintText(g);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1280, 1024);
    }
    
}
