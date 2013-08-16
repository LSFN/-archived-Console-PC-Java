package org.lsfn.console_pc.input_management;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.lsfn.console_pc.data_management.DataManager;

public class InputManager implements KeyListener, MouseListener {

    private String currentWidgetPath;
    private DataManager dataManager;
    
    public InputManager(DataManager screenManager) {
        this.currentWidgetPath = null;
        this.dataManager = dataManager;
    }
    
    @Override
    public void mouseClicked(MouseEvent arg0) {
        this.currentWidgetPath = this.dataManager.getWidgetPointedToPath(arg0.getPoint());
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
    public void keyPressed(KeyEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub
        
    }
    
}
