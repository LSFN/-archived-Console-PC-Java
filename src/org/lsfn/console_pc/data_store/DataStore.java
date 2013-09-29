package org.lsfn.console_pc.data_store;

import org.lsfn.console_pc.STS.STSdown;
import org.lsfn.console_pc.STS.STSup;
import org.lsfn.console_pc.data_store.sinks.ISinkBoolean;
import org.lsfn.console_pc.data_store.sinks.ISinkDouble;
import org.lsfn.console_pc.data_store.sinks.ISinkInteger;
import org.lsfn.console_pc.data_store.sinks.ISinkPoint;
import org.lsfn.console_pc.data_store.sinks.ISinkString;
import org.lsfn.console_pc.data_store.sinks.ISinkStringList;
import org.lsfn.console_pc.data_store.sinks.ISinkTrigger;
import org.lsfn.console_pc.data_store.sources.ISourceBoolean;
import org.lsfn.console_pc.data_store.sources.ISourceDouble;
import org.lsfn.console_pc.data_store.sources.ISourceInteger;
import org.lsfn.console_pc.data_store.sources.ISourcePoint;
import org.lsfn.console_pc.data_store.sources.ISourceString;
import org.lsfn.console_pc.data_store.sources.ISourceTrigger;

public class DataStore implements IDataStore {

    //private static final int tickInterval = 50;    
    private static final String starshipConnectionPrefix = "starshipConnection/";
    private static final String nebulaConnectionPrefix = "nebulaConnection/";
    private static final String lobbyPrefix = "lobby/";
    //private static final String pilotingPrefix = "piloting/";
    //private static final String visualSensorsPrefix = "visualSensors/";
    //private static final String shipDesignerPrefix = "shipDesigner/";
    
    
    private StarshipConnectionDataStore starshipConnectionDataStore;
    private NebulaConnectionDataStore nebulaConnectionDataStore;
    private LobbyDataStore lobbyDataStore;
    //private PilotingData pilotingData;
    //private VisualSensorsData visualSensorsData;
    //private ShipDesignerData shipDesignerData;
    
    public DataStore() {
        this.starshipConnectionDataStore = new StarshipConnectionDataStore();
        this.nebulaConnectionDataStore = new NebulaConnectionDataStore();
    }
    
    public void processInput(STSdown message) {
    	if(message.hasConnection()) {
    		this.nebulaConnectionDataStore.processInput(message.getConnection());
    	}
    }
    
    public STSup generateOutput() {
    	STSup.Connection stsUpConnection = this.nebulaConnectionDataStore.generateOutput();
    	
    	if(stsUpConnection != null /* more terms */) {
    		STSup.Builder stsUp = STSup.newBuilder();
    		if(stsUpConnection != null) {
    			stsUp.setConnection(stsUpConnection);
    		}
    		return stsUp.build();
    	}
    	return null;
    }
    
    @Override
    public ISourceBoolean findSourceBoolean(IDataPath dataPath) {
    	if(dataPath.topLevelMatch(starshipConnectionPrefix)) {
    		return this.starshipConnectionDataStore.findSourceBoolean(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(nebulaConnectionPrefix)) {
    		return this.nebulaConnectionDataStore.findSourceBoolean(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(lobbyPrefix)) {
    		return this.lobbyDataStore.findSourceBoolean(dataPath.stripTopLevel());
    	}
        return null;
    }

    @Override
    public ISourceString findSourceString(IDataPath dataPath) {
    	if(dataPath.topLevelMatch(starshipConnectionPrefix)) {
    		return this.starshipConnectionDataStore.findSourceString(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(nebulaConnectionPrefix)) {
    		return this.nebulaConnectionDataStore.findSourceString(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(lobbyPrefix)) {
    		return this.lobbyDataStore.findSourceString(dataPath.stripTopLevel());
    	}
        return null;
    }

    @Override
    public ISourceInteger findSourceInteger(IDataPath dataPath) {
    	if(dataPath.topLevelMatch(starshipConnectionPrefix)) {
    		return this.starshipConnectionDataStore.findSourceInteger(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(nebulaConnectionPrefix)) {
    		return this.nebulaConnectionDataStore.findSourceInteger(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(lobbyPrefix)) {
    		return this.lobbyDataStore.findSourceInteger(dataPath.stripTopLevel());
    	}
        return null;
    }
    
    @Override
    public ISourceDouble findSourceDouble(IDataPath dataPath) {
    	if(dataPath.topLevelMatch(starshipConnectionPrefix)) {
    		return this.starshipConnectionDataStore.findSourceDouble(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(nebulaConnectionPrefix)) {
    		return this.nebulaConnectionDataStore.findSourceDouble(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(lobbyPrefix)) {
    		return this.lobbyDataStore.findSourceDouble(dataPath.stripTopLevel());
    	}
        return null;
    }
    
    @Override
    public ISourceTrigger findSourceTrigger(IDataPath dataPath) {
    	if(dataPath.topLevelMatch(starshipConnectionPrefix)) {
    		return this.starshipConnectionDataStore.findSourceTrigger(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(nebulaConnectionPrefix)) {
    		return this.nebulaConnectionDataStore.findSourceTrigger(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(lobbyPrefix)) {
    		return this.lobbyDataStore.findSourceTrigger(dataPath.stripTopLevel());
    	}
        return null;
    }

    @Override
    public ISourcePoint findSourcePoint(IDataPath dataPath) {
    	if(dataPath.topLevelMatch(starshipConnectionPrefix)) {
    		return this.starshipConnectionDataStore.findSourcePoint(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(nebulaConnectionPrefix)) {
    		return this.nebulaConnectionDataStore.findSourcePoint(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(lobbyPrefix)) {
    		return this.lobbyDataStore.findSourcePoint(dataPath.stripTopLevel());
    	}
        return null;
    }

    @Override
    public ISinkBoolean findSinkBoolean(IDataPath dataPath) {
    	if(dataPath.topLevelMatch(starshipConnectionPrefix)) {
    		return this.starshipConnectionDataStore.findSinkBoolean(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(nebulaConnectionPrefix)) {
    		return this.nebulaConnectionDataStore.findSinkBoolean(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(lobbyPrefix)) {
    		return this.lobbyDataStore.findSinkBoolean(dataPath.stripTopLevel());
    	}
        return null;
    }
    
    @Override
    public ISinkString findSinkString(IDataPath dataPath) {
    	if(dataPath.topLevelMatch(starshipConnectionPrefix)) {
    		return this.starshipConnectionDataStore.findSinkString(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(nebulaConnectionPrefix)) {
    		return this.nebulaConnectionDataStore.findSinkString(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(lobbyPrefix)) {
    		return this.lobbyDataStore.findSinkString(dataPath.stripTopLevel());
    	}
        return null;
    }
    
    @Override
	public ISinkStringList findSinkStringList(IDataPath dataPath) {
    	if(dataPath.topLevelMatch(lobbyPrefix)) {
    		return this.lobbyDataStore.findSinkStringList(dataPath.stripTopLevel());
    	}
		return null;
	}

    @Override
    public ISinkInteger findSinkInteger(IDataPath dataPath) {
    	if(dataPath.topLevelMatch(starshipConnectionPrefix)) {
    		return this.starshipConnectionDataStore.findSinkInteger(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(nebulaConnectionPrefix)) {
    		return this.nebulaConnectionDataStore.findSinkInteger(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(lobbyPrefix)) {
    		return this.lobbyDataStore.findSinkInteger(dataPath.stripTopLevel());
    	}
        return null;
    }

    @Override
    public ISinkDouble findSinkDouble(IDataPath dataPath) {
    	if(dataPath.topLevelMatch(starshipConnectionPrefix)) {
    		return this.starshipConnectionDataStore.findSinkDouble(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(nebulaConnectionPrefix)) {
    		return this.nebulaConnectionDataStore.findSinkDouble(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(lobbyPrefix)) {
    		return this.lobbyDataStore.findSinkDouble(dataPath.stripTopLevel());
    	}
        return null;
    }

    @Override
    public ISinkTrigger findSinkTrigger(IDataPath dataPath) {
    	if(dataPath.topLevelMatch(starshipConnectionPrefix)) {
    		return this.starshipConnectionDataStore.findSinkTrigger(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(nebulaConnectionPrefix)) {
    		return this.nebulaConnectionDataStore.findSinkTrigger(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(lobbyPrefix)) {
    		return this.lobbyDataStore.findSinkTrigger(dataPath.stripTopLevel());
    	}
        return null;
    }

    @Override
    public ISinkPoint findSinkPoint(IDataPath dataPath) {
    	if(dataPath.topLevelMatch(starshipConnectionPrefix)) {
    		return this.starshipConnectionDataStore.findSinkPoint(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(nebulaConnectionPrefix)) {
    		return this.nebulaConnectionDataStore.findSinkPoint(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(lobbyPrefix)) {
    		return this.lobbyDataStore.findSinkPoint(dataPath.stripTopLevel());
    	}
        return null;
    }

}
