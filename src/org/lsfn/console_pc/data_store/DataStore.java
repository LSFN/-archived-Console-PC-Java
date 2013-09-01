package org.lsfn.console_pc.data_store;

import org.lsfn.console_pc.data_store.sinks.ISinkBoolean;
import org.lsfn.console_pc.data_store.sinks.ISinkInteger;
import org.lsfn.console_pc.data_store.sinks.ISinkString;
import org.lsfn.console_pc.data_store.sources.ISourceBoolean;
import org.lsfn.console_pc.data_store.sources.ISourceInteger;
import org.lsfn.console_pc.data_store.sources.ISourceString;

public class DataStore implements IDataStore {

    private static final int tickInterval = 50;    
    private static final String starshipConnectionPrefix = "starshipConnection/";
    private static final String nebulaConnectionPrefix = "nebulaConnection/";
    private static final String lobbyPrefix = "lobby/";
    private static final String pilotingPrefix = "piloting/";
    private static final String visualSensorsPrefix = "visualSensors/";
    private static final String shipDesignerPrefix = "shipDesigner/";
    
    
    private StarshipConnectionDataStore starshipConnectionDataStore;
    //private NebulaConnectionData nebulaConnectionData;
    //private LobbyData lobbyData;
    //private PilotingData pilotingData;
    //private VisualSensorsData visualSensorsData;
    //private ShipDesignerData shipDesignerData;
    
    public DataStore() {
        this.starshipConnectionDataStore = new StarshipConnectionDataStore();
    }
    
    @Override
    public ISourceBoolean findSourceBoolean(IDataPath dataPath) {
        return null;
    }

    @Override
    public ISourceString findSourceString(IDataPath dataPath) {
        if(dataPath.topLevelMatch(starshipConnectionPrefix)) {
            return this.starshipConnectionDataStore.findSourceString(dataPath.stripTopLevel());
        }
        return null;
    }

    @Override
    public ISourceInteger findSourceInteger(IDataPath dataPath) {
        if(dataPath.topLevelMatch(starshipConnectionPrefix)) {
            return this.starshipConnectionDataStore.findSourceInteger(dataPath.stripTopLevel());
        }
        return null;
    }

    @Override
    public ISinkBoolean findSinkBoolean(IDataPath dataPath) {
        return null;
    }
    
    @Override
    public ISinkString findSinkString(IDataPath dataPath) {
        if(dataPath.topLevelMatch(starshipConnectionPrefix)) {
            return this.starshipConnectionDataStore.findSinkString(dataPath.stripTopLevel());
        }
        return null;
    }

    @Override
    public ISinkInteger findSinkInteger(IDataPath dataPath) {
        if(dataPath.topLevelMatch(starshipConnectionPrefix)) {
            return this.starshipConnectionDataStore.findSinkInteger(dataPath.stripTopLevel());
        }
        return null;
    }

}
