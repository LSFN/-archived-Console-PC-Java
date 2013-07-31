package org.lsfn.console_pc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.lsfn.console_pc.STS.STSdown;
import org.lsfn.console_pc.STS.STSup;
import org.lsfn.console_pc.STS.STSdown.VisualSensors.SpaceObject.Type;
import org.lsfn.console_pc.SpaceObject.ObjectType;

public class DataManager {

    private StarshipConnection starshipConnection;
    // Received data
    // Eventually put these into separate classes
    private boolean connectionConnected;
    private boolean lobbyReadyState;
    private String lobbyShipName;
    private List<String> lobbyShipsInGame;
    private boolean lobbyGameStarted;
    private Set<SpaceObject> visualSensorsSpaceObjects;
    
    // Data to go upstream 
    private String desiredShipName;
    private boolean desiredShipNameChanged;
    private boolean desiredReadyState;
    private boolean desiredReadyStateChanged;
    /* Key state array indices correspondence chart:
    0: Turn Anti-clockwise
    1: Turn Clockwise
    2: Strafe Left
    3: Strafe Right
    4: Forward Thrust
    5: Backwards Thrust
    */
    private boolean[] desiredCommandStates;
    private boolean[] desiredCommandStatesChanged;
    
    public DataManager() {
        this.starshipConnection = new StarshipConnection();
        
        this.connectionConnected = false;
        this.lobbyReadyState = false;
        this.lobbyShipName = "Mungle Box";
        this.lobbyShipsInGame = new ArrayList<String>();
        this.lobbyGameStarted = false;
        this.visualSensorsSpaceObjects = new HashSet<SpaceObject>();
        
        this.desiredShipName = "Mungle Box";
        this.desiredShipNameChanged = false;
        this.desiredReadyState = false;
        this.desiredReadyStateChanged = false;
        this.desiredCommandStates = new boolean[6];
        for(int i = 0; i < this.desiredCommandStates.length; i++) {
            this.desiredCommandStates[i] = false;
        }
        this.desiredCommandStatesChanged = new boolean[6];
        for(int i = 0; i < this.desiredCommandStatesChanged.length; i++) {
            this.desiredCommandStatesChanged[i] = false;
        }
    }
    
    public void processInput() {
        if(this.starshipConnection.isConnected()) {
            for(STSdown message : this.starshipConnection.receiveMessagesFromStarship()) {
                if(message.hasConnection()) {
                    this.connectionConnected = message.getConnection().getConnected();
                }
                if(message.hasLobby()) {
                    STSdown.Lobby lobby = message.getLobby();
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
                if(message.hasVisualSensors()) {
                    this.visualSensorsSpaceObjects.clear();
                    for(STSdown.VisualSensors.SpaceObject so : message.getVisualSensors().getSpaceObjectsList()) {
                        SpaceObject.ObjectType objType = ObjectType.SHIP;
                        if(so.getType() == Type.ASTEROID) {
                            objType = ObjectType.ASTEROID;
                        }
                        this.visualSensorsSpaceObjects.add(new SpaceObject(objType, so.getPosition().getX(), so.getPosition().getY(), so.getOrientation()));
                    }
                }
            }
        }
    }
    
    public void generateOutput() {
        boolean lobbyStatesChanged = this.desiredReadyStateChanged || this.desiredShipNameChanged;
        boolean pilotingStatesChanged = false;
        for(int i = 0; i < this.desiredCommandStatesChanged.length; i++) {
            pilotingStatesChanged |= this.desiredCommandStatesChanged[i];
        }
        
        if(this.starshipConnection.isConnected() && (lobbyStatesChanged || pilotingStatesChanged)) {
            STSup.Builder stsUp = STSup.newBuilder();
            if(lobbyStatesChanged) {
                STSup.Lobby.Builder stsUpLobby = STSup.Lobby.newBuilder();
                if(this.desiredReadyStateChanged) {
                    stsUpLobby.setReadyState(desiredReadyState);
                }
                if(this.desiredShipNameChanged) {
                    stsUpLobby.setShipName(desiredShipName);
                }
                stsUp.setLobby(stsUpLobby);
            }
            if(pilotingStatesChanged) {
                STSup.Piloting.Builder stsUpPiloting = STSup.Piloting.newBuilder();
                if(this.desiredCommandStatesChanged[0]) {
                    stsUpPiloting.setTurnAnti(this.desiredCommandStates[0]);
                    this.desiredCommandStatesChanged[0] = false;
                }
                if(this.desiredCommandStatesChanged[1]) {
                    stsUpPiloting.setTurnClock(this.desiredCommandStates[1]);
                    this.desiredCommandStatesChanged[1] = false;
                }
                if(this.desiredCommandStatesChanged[2]) {
                    stsUpPiloting.setThrustLeft(this.desiredCommandStates[2]);
                    this.desiredCommandStatesChanged[2] = false;
                }
                if(this.desiredCommandStatesChanged[3]) {
                    stsUpPiloting.setThrustRight(this.desiredCommandStates[3]);
                    this.desiredCommandStatesChanged[3] = false;
                }
                if(this.desiredCommandStatesChanged[4]) {
                    stsUpPiloting.setThrustForward(this.desiredCommandStates[4]);
                    this.desiredCommandStatesChanged[4] = false;
                }
                if(this.desiredCommandStatesChanged[5]) {
                    stsUpPiloting.setThrustBackward(this.desiredCommandStates[5]);
                    this.desiredCommandStatesChanged[5] = false;
                }
                stsUp.setPiloting(stsUpPiloting);
            }
            this.starshipConnection.sendMessageToStarship(stsUp.build());
        }
    }
    
    public boolean connectToStarship(String host, Integer port) {
        return this.starshipConnection.connect(host, port);
    }
    
    public boolean isConnectedToStarship() {
        return this.starshipConnection.isConnected();
    }

    public void connectToNebula(String host, Integer port) {
        if(this.starshipConnection.isConnected() && !this.connectionConnected) {
            STSup.Builder stsUp = STSup.newBuilder();
            STSup.Connection.Builder stsUpConnection = STSup.Connection.newBuilder();
            stsUpConnection.setConnectionCommand(STSup.Connection.ConnectionCommand.CONNECT);
            stsUpConnection.setHost(host);
            stsUpConnection.setPort(port);
            stsUp.setConnection(stsUpConnection);
            this.starshipConnection.sendMessageToStarship(stsUp.build());
        }
    }
    
    public void disconnectFromNebula() {
        if(this.starshipConnection.isConnected() && this.connectionConnected) {
            STSup.Builder stsUp = STSup.newBuilder();
            STSup.Connection.Builder stsUpConnection = STSup.Connection.newBuilder();
            stsUpConnection.setConnectionCommand(STSup.Connection.ConnectionCommand.DISCONNECT);
            stsUp.setConnection(stsUpConnection);
            this.starshipConnection.sendMessageToStarship(stsUp.build());
        }
    }
    
    public boolean isConnectionConnected() {
        return connectionConnected;
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

    public boolean isLobbyGameStarted() {
        return lobbyGameStarted;
    }

    public Set<SpaceObject> getVisualSensorsSpaceObjects() {
        return visualSensorsSpaceObjects;
    }

    public void setDesiredShipName(String desiredShipName) {
        this.desiredShipName = desiredShipName;
        this.desiredShipNameChanged = true;
    }

    public void setDesiredReadyState(boolean desiredReadyState) {
        this.desiredReadyState = desiredReadyState;
        this.desiredReadyStateChanged = true;
    }
    
    public void setTurnAnti(boolean state) {
        this.desiredCommandStates[0] = state;
        this.desiredCommandStatesChanged[0] = true;
    }
    
    public void setTurnClock(boolean state) {
        this.desiredCommandStates[1] = state;
        this.desiredCommandStatesChanged[1] = true;
    }
    
    public void setThrustLeft(boolean state) {
        this.desiredCommandStates[2] = state;
        this.desiredCommandStatesChanged[2] = true;
    }
    
    public void setThrustRight(boolean state) {
        this.desiredCommandStates[3] = state;
        this.desiredCommandStatesChanged[3] = true;
    }
    
    public void setThrustForward(boolean state) {
        this.desiredCommandStates[4] = state;
        this.desiredCommandStatesChanged[4] = true;
    }
    
    public void setThrustBackward(boolean state) {
        this.desiredCommandStates[5] = state;
        this.desiredCommandStatesChanged[5] = true;
    }
}
