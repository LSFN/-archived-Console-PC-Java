package org.lsfn.console_pc;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TypingKeyAdapter extends KeyAdapter {

    private String buffer;
    
    public TypingKeyAdapter() {
        this.buffer = "";
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        char character = e.getKeyChar();
        int code = e.getKeyCode();
        
        if((int)character >= (int)'a' && (int)character <= (int)'z') {
            this.buffer = this.buffer + character;
        } else if(code == KeyEvent.VK_BACK_SPACE) {
            this.buffer = this.buffer.substring(0, this.buffer.length() - 1);
        }
        
    }
    
    public String getBuffer() {
        String buf = new String(this.buffer);
        this.buffer = "";
        return buf;
    }
    
}
