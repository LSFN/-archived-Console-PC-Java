package org.lsfn.console_pc;

import javax.swing.JFrame;

import org.lsfn.console_pc.bindings.BindingManager;
import org.lsfn.console_pc.data_store.DataStore;
import org.lsfn.console_pc.input.InputManager;
import org.lsfn.console_pc.screen.ScreenManager;
import org.lsfn.console_pc.widgets.WidgetManager;

public class ConsoleManager {

    private StarshipConnection starshipConnection;
    private DataStore dataStore;
    private ScreenManager screenManager;
    private WidgetManager widgetManager;
    private BindingManager bindingManager;
    private InputManager inputManager;
    
    public ConsoleManager(JFrame parent) {
        this.starshipConnection = new StarshipConnection();
        this.dataStore = new DataStore();
        this.screenManager = new ScreenManager(this.dataStore);
        parent.add(this.screenManager);
        parent.pack();
        this.widgetManager = new WidgetManager(this.dataStore);
        this.bindingManager = new BindingManager(this.dataStore);
        this.inputManager = new InputManager(this.widgetManager, this.bindingManager);
        parent.addKeyListener(inputManager);
        this.screenManager.addMouseListener(this.inputManager);
    }
}
