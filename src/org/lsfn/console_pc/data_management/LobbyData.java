package org.lsfn.console_pc.data_management;

import org.lsfn.console_pc.STS.STSdown;
import org.lsfn.console_pc.STS.STSup;

public class LobbyData {

    // Input
    private UnchangingBoolean lobbyReadyState;
    private UnchangingString lobbyShipName;
    private UnchangingList<String> lobbyShipsInGame;
    private UnchangingBoolean lobbyGameStarted;
    
    // Output
    private UpdatableString desiredShipName;
    private UpdatableBoolean desiredReadyState;
    
    public LobbyData() {
        this.lobbyReadyState = new UnchangingBoolean(false);
        this.lobbyShipName = new UnchangingString("Mungle Box");
        this.lobbyShipsInGame = new UnchangingList<String>();
        this.lobbyGameStarted = new UnchangingBoolean(false);
        
        this.desiredShipName = new UpdatableString("Mungle Box");
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
            this.lobbyShipsInGame.clear();
            this.lobbyShipsInGame.addAll(lobby.getShipsInGameList());
        }
        if(lobby.hasGameStarted()) {
            this.lobbyGameStarted.setData(lobby.getGameStarted());
        }
    }
    
    public STSup.Lobby generateOutput() {
        if(this.desiredReadyState.isFlagSet() || this.desiredShipName.isFlagSet()) {
            STSup.Lobby.Builder lobby = STSup.Lobby.newBuilder();
            if(this.desiredReadyState.isFlagSet()) {
                lobby.setReadyState(this.desiredReadyState.getData());
                this.desiredReadyState.resetFlag();
            }
            if(this.desiredShipName.isFlagSet()) {
                lobby.setShipName(this.desiredShipName.getData());
                this.desiredShipName.resetFlag();
            }
            return lobby.build();
        }
        return null;
    }
}
