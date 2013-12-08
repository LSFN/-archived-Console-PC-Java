package org.lsfn.console_pc.data_store;

import org.lsfn.common.STS.STSdown;
import org.lsfn.common.STS.STSup;
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
    private static final String starshipConnectionPrefix = "starshipConnection";
    private static final String nebulaConnectionPrefix = "nebulaConnection";
    private static final String lobbyPrefix = "lobby";
    private static final String reactorPrefix = "reactor";
    private static final String powerDistributionPrefix = "powerDistribution";
    private static final String enginesPrefix = "engines";
    private static final String thrustersPrefix = "thrusters";
    
    private StarshipConnectionDataStore starshipConnectionDataStore;
    private NebulaConnectionDataStore nebulaConnectionDataStore;
    private LobbyDataStore lobbyDataStore;
    private ReactorDataStore reactorDataStore;
    private PowerDistributionDataStore powerDistributionDataStore;
    private EnginesDataStore enginesDataStore;
    private ThrustersDataStore thrustersDataStore;
    
    public DataStore() {
        this.starshipConnectionDataStore = new StarshipConnectionDataStore();
        this.nebulaConnectionDataStore = new NebulaConnectionDataStore();
        this.lobbyDataStore = new LobbyDataStore();
        this.reactorDataStore = new ReactorDataStore();
        this.powerDistributionDataStore = new PowerDistributionDataStore();
        this.enginesDataStore = new EnginesDataStore();
        this.thrustersDataStore = new ThrustersDataStore();
    }
    
    public void processInput(STSdown message) {
    	if(message.hasConnection()) {
    		this.nebulaConnectionDataStore.processInput(message.getConnection());
    	}
    	if(message.hasLobby()) {
    		this.lobbyDataStore.processInput(message.getLobby());
    	}
    }
    
    public STSup generateOutput() {
    	STSup.Connection stsUpConnection = this.nebulaConnectionDataStore.generateOutput();
    	STSup.Lobby stsUpLobby = this.lobbyDataStore.generateOutput();
    	
    	if(stsUpConnection != null || stsUpLobby != null) {
    		STSup.Builder stsUp = STSup.newBuilder();
    		if(stsUpConnection != null) {
    			stsUp.setConnection(stsUpConnection);
    		}
    		if(stsUpLobby != null) {
    			stsUp.setLobby(stsUpLobby);
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
    	} else if(dataPath.topLevelMatch(reactorPrefix)) {
    		return this.reactorDataStore.findSourceBoolean(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(powerDistributionPrefix)) {
    		return this.powerDistributionDataStore.findSourceBoolean(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(enginesPrefix)) {
    		return this.enginesDataStore.findSourceBoolean(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(thrustersPrefix)) {
    		return this.thrustersDataStore.findSourceBoolean(dataPath.stripTopLevel());
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
    	} else if(dataPath.topLevelMatch(reactorPrefix)) {
    		return this.reactorDataStore.findSourceString(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(powerDistributionPrefix)) {
    		return this.powerDistributionDataStore.findSourceString(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(enginesPrefix)) {
    		return this.enginesDataStore.findSourceString(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(thrustersPrefix)) {
    		return this.thrustersDataStore.findSourceString(dataPath.stripTopLevel());
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
    	} else if(dataPath.topLevelMatch(reactorPrefix)) {
    		return this.reactorDataStore.findSourceInteger(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(powerDistributionPrefix)) {
    		return this.powerDistributionDataStore.findSourceInteger(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(enginesPrefix)) {
    		return this.enginesDataStore.findSourceInteger(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(thrustersPrefix)) {
    		return this.thrustersDataStore.findSourceInteger(dataPath.stripTopLevel());
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
    	} else if(dataPath.topLevelMatch(reactorPrefix)) {
    		return this.reactorDataStore.findSourceDouble(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(powerDistributionPrefix)) {
    		return this.powerDistributionDataStore.findSourceDouble(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(enginesPrefix)) {
    		return this.enginesDataStore.findSourceDouble(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(thrustersPrefix)) {
    		return this.thrustersDataStore.findSourceDouble(dataPath.stripTopLevel());
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
    	} else if(dataPath.topLevelMatch(reactorPrefix)) {
    		return this.reactorDataStore.findSourceTrigger(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(powerDistributionPrefix)) {
    		return this.powerDistributionDataStore.findSourceTrigger(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(enginesPrefix)) {
    		return this.enginesDataStore.findSourceTrigger(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(thrustersPrefix)) {
    		return this.thrustersDataStore.findSourceTrigger(dataPath.stripTopLevel());
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
    	} else if(dataPath.topLevelMatch(reactorPrefix)) {
    		return this.reactorDataStore.findSourcePoint(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(powerDistributionPrefix)) {
    		return this.powerDistributionDataStore.findSourcePoint(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(enginesPrefix)) {
    		return this.enginesDataStore.findSourcePoint(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(thrustersPrefix)) {
    		return this.thrustersDataStore.findSourcePoint(dataPath.stripTopLevel());
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
    	} else if(dataPath.topLevelMatch(reactorPrefix)) {
    		return this.reactorDataStore.findSinkBoolean(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(powerDistributionPrefix)) {
    		return this.powerDistributionDataStore.findSinkBoolean(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(enginesPrefix)) {
    		return this.enginesDataStore.findSinkBoolean(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(thrustersPrefix)) {
    		return this.thrustersDataStore.findSinkBoolean(dataPath.stripTopLevel());
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
    	} else if(dataPath.topLevelMatch(reactorPrefix)) {
    		return this.reactorDataStore.findSinkString(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(powerDistributionPrefix)) {
    		return this.powerDistributionDataStore.findSinkString(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(enginesPrefix)) {
    		return this.enginesDataStore.findSinkString(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(thrustersPrefix)) {
    		return this.thrustersDataStore.findSinkString(dataPath.stripTopLevel());
    	}
        return null;
    }
    
    @Override
	public ISinkStringList findSinkStringList(IDataPath dataPath) {
    	if(dataPath.topLevelMatch(lobbyPrefix)) {
    		return this.lobbyDataStore.findSinkStringList(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(nebulaConnectionPrefix)) {
    		return this.nebulaConnectionDataStore.findSinkStringList(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(lobbyPrefix)) {
    		return this.lobbyDataStore.findSinkStringList(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(reactorPrefix)) {
    		return this.reactorDataStore.findSinkStringList(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(powerDistributionPrefix)) {
    		return this.powerDistributionDataStore.findSinkStringList(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(enginesPrefix)) {
    		return this.enginesDataStore.findSinkStringList(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(thrustersPrefix)) {
    		return this.thrustersDataStore.findSinkStringList(dataPath.stripTopLevel());
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
    	} else if(dataPath.topLevelMatch(reactorPrefix)) {
    		return this.reactorDataStore.findSinkInteger(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(powerDistributionPrefix)) {
    		return this.powerDistributionDataStore.findSinkInteger(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(enginesPrefix)) {
    		return this.enginesDataStore.findSinkInteger(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(thrustersPrefix)) {
    		return this.thrustersDataStore.findSinkInteger(dataPath.stripTopLevel());
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
    	} else if(dataPath.topLevelMatch(reactorPrefix)) {
    		return this.reactorDataStore.findSinkDouble(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(powerDistributionPrefix)) {
    		return this.powerDistributionDataStore.findSinkDouble(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(enginesPrefix)) {
    		return this.enginesDataStore.findSinkDouble(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(thrustersPrefix)) {
    		return this.thrustersDataStore.findSinkDouble(dataPath.stripTopLevel());
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
    	} else if(dataPath.topLevelMatch(reactorPrefix)) {
    		return this.reactorDataStore.findSinkTrigger(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(powerDistributionPrefix)) {
    		return this.powerDistributionDataStore.findSinkTrigger(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(enginesPrefix)) {
    		return this.enginesDataStore.findSinkTrigger(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(thrustersPrefix)) {
    		return this.thrustersDataStore.findSinkTrigger(dataPath.stripTopLevel());
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
    	} else if(dataPath.topLevelMatch(reactorPrefix)) {
    		return this.reactorDataStore.findSinkPoint(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(powerDistributionPrefix)) {
    		return this.powerDistributionDataStore.findSinkPoint(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(enginesPrefix)) {
    		return this.enginesDataStore.findSinkPoint(dataPath.stripTopLevel());
    	} else if(dataPath.topLevelMatch(thrustersPrefix)) {
    		return this.thrustersDataStore.findSinkPoint(dataPath.stripTopLevel());
    	}
        return null;
    }

}
