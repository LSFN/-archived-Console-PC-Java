package org.lsfn.console_pc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class PilotingPanel extends JPanel {
    
    public PilotingPanel() {
        
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        System.out.println(this.getBounds());
        g.setColor(Color.BLACK);
        g.fillRect(0,  0, 100,  100);
        g.setColor(Color.RED);
        g.fillRect(10, 10, 90, 90);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1280, 1024);
    }
}
