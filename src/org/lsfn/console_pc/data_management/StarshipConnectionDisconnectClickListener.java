package org.lsfn.console_pc.data_management;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.lsfn.console_pc.data_management.elements.ControlledData;

public class StarshipConnectionDisconnectClickListener implements ControlledData {

    private StarshipConnectionData starshipConnectionData;
    
    public StarshipConnectionDisconnectClickListener(StarshipConnectionData starshipConnectionData) {
        this.starshipConnectionData = starshipConnectionData;
    }
    
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
        this.starshipConnectionData.disconnect();
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
