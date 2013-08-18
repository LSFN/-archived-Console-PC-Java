package org.lsfn.console_pc.data_management.elements;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class TypeableString implements DataSource, ControlledData {

    private String data;
    private boolean flag;
    
    public TypeableString(String data) {
        this.data = data;
        this.flag = false;
    }
    
    public String getData() {
        return this.data;
    }
    
    public void resetFlag() {
        this.flag = false;
    }
    
    public boolean flagRaised() {
        return this.flag;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        char c = e.getKeyChar();
        if((int)c >= (int)' ' && (int)c <= (int)'~') {
            this.data += c;
            this.flag = true;
        } else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE && this.data.length() > 0) {
            this.data = this.data.substring(0, this.data.length() - 1);
            this.flag = true;
        }
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
