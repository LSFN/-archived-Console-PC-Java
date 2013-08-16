package org.lsfn.console_pc.data_management.elements;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class TypeableInteger implements ControlledData, DataSource {

    private Integer data;
    
    public TypeableInteger(Integer data) {
        this.data = data;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        // This allows an integer to be typed out as if it were a string
        // Positive integers only
        char c = e.getKeyChar();
        if((int)c >= (int)'0' && (int)c <= (int)'9') {
            this.data *= 10;
            this.data += (int)c - (int)'0';
        } else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            this.data /= 10; 
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {

    }

    @Override
    public void keyTyped(KeyEvent arg0) {

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

    @Override
    public Object getData() {
        return data;
    }

}
