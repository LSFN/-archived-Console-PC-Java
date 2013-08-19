package org.lsfn.console_pc.data_management.elements;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class UpdatableBoolean implements ControlledData {

    private boolean data;
    private boolean flag;
    
    public UpdatableBoolean(boolean data) {
        this.data = data;
        this.flag = false;
    }
    
    public void setData(boolean data) {
        if(this.data != data) {
            this.data = data;
            this.flag = true;
        }
    }
    
    public boolean getData() {
        return this.data;
    }
    
    public boolean flagRaised() {
        return this.flag;
    }
    
    public void resetFlag() {
        this.flag = false;
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
        this.data = !this.data;
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
