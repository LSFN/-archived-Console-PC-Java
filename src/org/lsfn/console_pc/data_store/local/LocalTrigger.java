package org.lsfn.console_pc.data_store.local;

import org.lsfn.console_pc.data_store.sinks.ISinkTrigger;
import org.lsfn.console_pc.data_store.sources.ISourceTrigger;

public class LocalTrigger implements ISourceTrigger, ISinkTrigger {

    private int trigger;
    
    public LocalTrigger() {
        this.trigger = 0;
    }
    
    @Override
    public boolean isTriggered() {
        if(this.trigger > 0) {
            this.trigger--;
            return true;
        }
        return false;
    }

    @Override
    public void trigger() {
        this.trigger++;
    }

}
