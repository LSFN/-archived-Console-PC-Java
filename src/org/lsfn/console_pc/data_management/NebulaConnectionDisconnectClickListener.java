package org.lsfn.console_pc.data_management;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.lsfn.console_pc.data_management.elements.ControlledData;

public class NebulaConnectionDisconnectClickListener implements ControlledData {

    private NebulaConnectionData nebulaConnectionData;
    
    public NebulaConnectionDisconnectClickListener(NebulaConnectionData nebulaConnectionData) {
        this.nebulaConnectionData = nebulaConnectionData;
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
        this.nebulaConnectionData.disconnect();
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
