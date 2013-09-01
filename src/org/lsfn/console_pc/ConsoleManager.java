package org.lsfn.console_pc;

import org.lsfn.console_pc.data_store.DataStore;

public class ConsoleManager {

    private StarshipConnection starshipConnection;
    private DataStore dataStore;
    
    public ConsoleManager() {
        this.starshipConnection = new StarshipConnection();
        this.dataStore = new DataStore();
    }
}
