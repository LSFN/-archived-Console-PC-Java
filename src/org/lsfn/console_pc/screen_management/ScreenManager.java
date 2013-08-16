package org.lsfn.console_pc.screen_management;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import org.lsfn.console_pc.data_management.DataManager;

public class ScreenManager extends JPanel implements HierarchyBoundsListener {

    /**
     * 
     */
    private static final long serialVersionUID = -5930093272563853637L;
    private Screen screen;
    private Map<String, Screen> screens;
    
    public ScreenManager() {
        this.addHierarchyBoundsListener(this);
        
        this.screens = new HashMap<String, Screen>();
        this.screen = null;
    }

    public void setScreens(Map<String, Screen> screens) {
        this.screens = screens;
    }
    
    public void changeScreen(String screenName) {
        this.screen = this.screens.get(screenName);
        if(this.screen != null) {
            this.screen.setBounds(getBounds());
            //this.screen.linkOutputs(this.dataManager.getOutputsFromPaths(this.screen.getWidgetMapping().values()));
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2d = (Graphics2D)g;
        if(this.screen == null) {
            g2d.setColor(Color.BLACK);
            g2d.fill(getBounds());
            g2d.setColor(Color.RED);
            g2d.drawString("ERROR", getBounds().width/2, getBounds().height/2);
        } else {
            this.screen.drawScreen(g2d);
        }
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1280, 1024);
    }
    
    public String getWidgetPath(Point p) {
        if(this.screen != null) {
            return this.screen.getWidgetPath(p);
        }
        return null;
    }
    
    @Override
    public void ancestorMoved(HierarchyEvent e) {
    }

    @Override
    public void ancestorResized(HierarchyEvent e) {
        // recalculate the positions of the widgets
        if(this.screen != null) {
            this.screen.setBounds(getBounds());
        }
    }
    
    
}
