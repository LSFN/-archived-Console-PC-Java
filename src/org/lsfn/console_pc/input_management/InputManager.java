package org.lsfn.console_pc.input_management;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.lsfn.console_pc.data_management.DataManager;
import org.lsfn.console_pc.data_management.elements.ControlledData;

public class InputManager implements ControlledData {

    private DataManager dataManager;
    private String currentWidgetPath;
    private ControlledData selectedDataToControl;
    
    public InputManager(DataManager dataManager) {
        this.dataManager = dataManager;
        this.currentWidgetPath = null;
        this.selectedDataToControl = null;
    }
    
    @Override
    public void mouseClicked(MouseEvent arg0) {
        this.currentWidgetPath = this.dataManager.getWidgetPointedToPath(arg0.getPoint());
        this.selectedDataToControl = this.dataManager.findControlledData(this.currentWidgetPath);
        if(this.selectedDataToControl != null) {
            this.selectedDataToControl.mouseClicked(arg0);
        }
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        if(this.selectedDataToControl != null) {
            this.selectedDataToControl.mouseEntered(arg0);
        }
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        if(this.selectedDataToControl != null) {
            this.selectedDataToControl.mouseExited(arg0);
        }
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        if(this.selectedDataToControl != null) {
            this.selectedDataToControl.mousePressed(arg0);
        }
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        if(this.selectedDataToControl != null) {
            this.selectedDataToControl.mouseReleased(arg0);
        }
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        if(this.selectedDataToControl != null) {
            this.selectedDataToControl.keyPressed(arg0);
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        if(this.selectedDataToControl != null) {
            this.selectedDataToControl.keyReleased(arg0);
        }
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        if(this.selectedDataToControl != null) {
            this.selectedDataToControl.keyTyped(arg0);
        }
    }
    
}
