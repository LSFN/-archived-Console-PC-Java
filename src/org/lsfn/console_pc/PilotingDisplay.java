package org.lsfn.console_pc;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

public class PilotingDisplay extends Thread {
    
    private enum DisplayState {
        MENU,
        LOBBY,
        PILOTING
    }
    private DisplayState displayState;
    
    public PilotingDisplay() {
        // This constructor is very subtle.
        // Note how it appears to do nothing.
        displayState = DisplayState.MENU;
    }
    
    @Override
    public void run() {
        try {
            Display.setDisplayMode(new DisplayMode(1280, 1024));
            Display.create();
        } catch (LWJGLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        while(!Display.isCloseRequested()) {
            
            Display.update();
        }
        
        Display.destroy();
    }
}
