package org.lsfn.console_pc.custom_consoles.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.lsfn.console_pc.custom_consoles.bindings.IBindingManager;
import org.lsfn.console_pc.custom_consoles.widgets.IWidgetInfo;
import org.lsfn.console_pc.custom_consoles.widgets.IWidgetManager;

public class InputManager implements KeyListener, MouseListener {
    
    private IWidgetManager widgetManager;
    private IBindingManager bindingManager;
    
    public InputManager(IWidgetManager widgetManager, IBindingManager bindingManager) {
        this.widgetManager = widgetManager;
        this.bindingManager = bindingManager;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        IWidgetInfo widgetInfo = this.widgetManager.getWidgetInfoForPoint(mouseEvent.getPoint());
        InputEvent inputEvent = new InputEvent(mouseEvent, true, widgetInfo);
        this.bindingManager.input(inputEvent);
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        InputEvent inputEvent = new InputEvent(keyEvent, true);
        this.bindingManager.input(inputEvent);
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        InputEvent inputEvent = new InputEvent(keyEvent, false);
        this.bindingManager.input(inputEvent);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        // TODO Auto-generated method stub
        
    }

}
