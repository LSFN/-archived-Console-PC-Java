package org.lsfn.console_pc.data_management.elements;

public class UpdatableBoolean {

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
}
