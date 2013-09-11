package org.lsfn.console_pc.bindings;

import java.util.HashMap;
import java.util.Map;

import org.lsfn.console_pc.data_store.IDataStore;
import org.lsfn.console_pc.input.InputEvent;
import org.lsfn.console_pc.screen.ScreenFile2.ScreenConfig2;

public class BindingManager implements IBindingManager {

    private IBinding currentBinding;
    private IDataStore dataStore;
    private Map<String, IBinding> bindings;
    
    public BindingManager(IDataStore dataStore) {
        this.currentBinding = null;
        this.dataStore = dataStore;
        this.bindings = new HashMap<String, IBinding>();
    }
    
    @Override
    public void input(InputEvent inputEvent) {
        if(this.currentBinding != null) {
            this.currentBinding.input(inputEvent);
        }
    }

    @Override
    public void registerBindings(String name, ScreenConfig2.Bindings bindings) {
        this.bindings.put(name, new Binding(bindings, this.dataStore));
    }

}
