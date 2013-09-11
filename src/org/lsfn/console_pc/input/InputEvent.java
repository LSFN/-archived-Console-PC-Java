package org.lsfn.console_pc.input;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.lsfn.console_pc.widgets.IWidgetInfo;

public class InputEvent {
    
    private KeyEvent keyEvent;
    private MouseEvent mouseEvent;
    private IWidgetInfo widgetInfo;
    private boolean pressedState;
    
    public InputEvent(KeyEvent keyEvent, boolean presssedState) {
        this.keyEvent = keyEvent;
        this.pressedState = presssedState;
    }
    
    public InputEvent(MouseEvent mouseEvent, boolean pressedState, IWidgetInfo widgetInfo) {
        this.mouseEvent = mouseEvent;
        this.pressedState = pressedState;
        this.widgetInfo = widgetInfo;
    }

    public KeyEvent getKeyEvent() {
        return keyEvent;
    }

    public MouseEvent getMouseEvent() {
        return mouseEvent;
    }

    public IWidgetInfo getWidgetInfo() {
        return widgetInfo;
    }
    
    public boolean getPressedState() {
        return pressedState;
    }
    
}
