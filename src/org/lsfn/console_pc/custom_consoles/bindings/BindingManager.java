package org.lsfn.console_pc.custom_consoles.bindings;

import java.util.HashMap;
import java.util.Map;

import org.lsfn.console_pc.custom_consoles.input.InputEvent;
import org.lsfn.console_pc.custom_consoles.screen.ScreenFile.ScreenConfig;
import org.lsfn.console_pc.data_store.IDataStore;

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
    public void registerBindings(String name, ScreenConfig.Bindings bindings) {
        this.bindings.put(name, new Binding(bindings, this.dataStore));
    }

	@Override
	public void setCurrentBindings(String name) {
		this.currentBinding = this.bindings.get(name);
	}

}
