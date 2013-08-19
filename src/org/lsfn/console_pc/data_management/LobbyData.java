package org.lsfn.console_pc.data_management;

import java.util.ArrayList;

import org.lsfn.console_pc.STS.STSdown;
import org.lsfn.console_pc.STS.STSup;
import org.lsfn.console_pc.data_management.elements.BooleanDataSource;
import org.lsfn.console_pc.data_management.elements.ControlledData;
import org.lsfn.console_pc.data_management.elements.DataSource;
import org.lsfn.console_pc.data_management.elements.ObjectDataSource;
import org.lsfn.console_pc.data_management.elements.StringDataSource;
import org.lsfn.console_pc.data_management.elements.TypeableString;
import org.lsfn.console_pc.data_management.elements.UpdatableBoolean;

public class LobbyData {

    // Input
    private BooleanDataSource lobbyReadyState;
    private StringDataSource lobbyShipName;
    private ObjectDataSource lobbyShipsInGame;
    private BooleanDataSource lobbyGameStarted;
    
    // Output
    private TypeableString desiredShipName;
    private UpdatableBoolean desiredReadyState;
    
    public LobbyData() {
        this.lobbyReadyState = new BooleanDataSource(false);
        this.lobbyShipName = new StringDataSource("Mungle Box");
        this.lobbyShipsInGame = new ObjectDataSource(new ArrayList<String>());
        this.lobbyGameStarted = new BooleanDataSource(false);
        
        this.desiredShipName = new TypeableString("Mungle Box");
        this.desiredReadyState = new UpdatableBoolean(false);
    }

    public void processLobby(STSdown.Lobby lobby) {
        if(lobby.hasReadyState()) {
            this.lobbyReadyState.setData(lobby.getReadyState());
        }
        if(lobby.hasShipName()) {
            this.lobbyShipName.setData(lobby.getShipName());
        }
        if(lobby.getShipsInGameCount() > 0) {
            this.lobbyShipsInGame.setData(lobby.getShipsInGameList());
        }
        if(lobby.hasGameStarted()) {
            this.lobbyGameStarted.setData(lobby.getGameStarted());
        }
    }
    
    public STSup.Lobby generateOutput() {
        if(this.desiredReadyState.flagRaised() || this.desiredShipName.flagRaised()) {
            STSup.Lobby.Builder lobby = STSup.Lobby.newBuilder();
            if(this.desiredReadyState.flagRaised()) {
                lobby.setReadyState(this.desiredReadyState.getData());
                this.desiredReadyState.resetFlag();
            }
            if(this.desiredShipName.flagRaised()) {
                lobby.setShipName(this.desiredShipName.getData());
                this.desiredShipName.resetFlag();
            }
            return lobby.build();
        }
        return null;
    }
    
    public DataSource getDataSourceFromPath(String dataPath) {
        if(dataPath.equals("nebulaReady")) {
            return this.lobbyReadyState;
        } else if(dataPath.equals("nebulaShipName")) {
            return this.lobbyShipName;
        } else if(dataPath.equals("shipName")) {
            return this.desiredShipName;
        } else if(dataPath.equals("shipList")) {
            return this.lobbyShipsInGame;
        } else if(dataPath.equals("gameStarted")) {
            return this.lobbyGameStarted;
        }
        return null;
    }

    public ControlledData getControlledDataFromPath(String dataPath) {
        if(dataPath.equals("shipName")) {
            return this.desiredShipName;
        } else if(dataPath.equals("ready")) {
            return this.desiredReadyState;
        }
        return null;
    }
}
