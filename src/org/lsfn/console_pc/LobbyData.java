package org.lsfn.console_pc;

import java.util.ArrayList;
import java.util.List;

import org.lsfn.console_pc.STS.STSdown;
import org.lsfn.console_pc.STS.STSup;

public class LobbyData {

    // Output
    private boolean lobbyReadyState;
    private String lobbyShipName;
    private List<String> lobbyShipsInGame;
    private boolean lobbyGameStarted;
    
    // Input
    private String desiredShipName;
    private boolean desiredShipNameChanged;
    private boolean desiredReadyState;
    private boolean desiredReadyStateChanged;
    
    public LobbyData() {
        this.lobbyReadyState = false;
        this.lobbyShipName = "Mungle Box";
        this.lobbyShipsInGame = new ArrayList<String>();
        this.lobbyGameStarted = false;
        
        this.desiredShipName = "Mungle Box";
        this.desiredShipNameChanged = false;
        this.desiredReadyState = false;
        this.desiredReadyStateChanged = false;
    }

    public boolean getLobbyReadyState() {
        return lobbyReadyState;
    }

    public String getLobbyShipName() {
        return lobbyShipName;
    }

    public List<String> getLobbyShipsInGame() {
        return lobbyShipsInGame;
    }

    public boolean getLobbyGameStarted() {
        return lobbyGameStarted;
    }
    
    public void processLobby(STSdown.Lobby lobby) {
        if(lobby.hasReadyState()) {
            this.lobbyReadyState = lobby.getReadyState();
        }
        if(lobby.hasShipName()) {
            this.lobbyShipName = lobby.getShipName();
        }
        if(lobby.getShipsInGameCount() > 0) {
            this.lobbyShipsInGame = lobby.getShipsInGameList();
        }
        if(lobby.hasGameStarted()) {
            this.lobbyGameStarted = lobby.getGameStarted();
        }
    }
    
    public void setShipName(String name) {
        if(!this.desiredShipName.equals(name)) {
            this.desiredShipName = name;
            this.desiredShipNameChanged = true;
        }
    }
    
    public void setReadyState(boolean ready) {
        if(this.desiredReadyState != ready) {
            this.desiredReadyState = ready;
            this.desiredReadyStateChanged = true;
        }
    }
    
    public STSup.Lobby generateOutput() {
        if(this.desiredReadyStateChanged || this.desiredShipNameChanged) {
            STSup.Lobby.Builder lobby = STSup.Lobby.newBuilder();
            if(this.desiredReadyStateChanged) {
                lobby.setReadyState(desiredReadyState);
                this.desiredReadyStateChanged = false;
            }
            if(this.desiredShipNameChanged) {
                lobby.setShipName(desiredShipName);
                this.desiredShipNameChanged = false;
            }
            return lobby.build();
        }
        return null;
    }
}
