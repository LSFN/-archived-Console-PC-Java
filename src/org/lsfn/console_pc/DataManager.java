package org.lsfn.console_pc;

import org.lsfn.console_pc.STS.STSdown;
import org.lsfn.console_pc.STS.STSup;

public class DataManager {

    private StarshipConnection starshipConnection;
    
    private ConnectionData connectionData;
    private LobbyData lobbyData;
    private PilotingData pilotingData;
    private VisualSensorsData visualSensorsData;
    
    // Horrible hack
    private boolean enteredShipDesigner;
    
    public DataManager() {
        this.starshipConnection = new StarshipConnection();
        
        this.connectionData = new ConnectionData();
        this.lobbyData = new LobbyData();
        this.pilotingData = new PilotingData();
        this.visualSensorsData = new VisualSensorsData();
    }
    
    public void processInput() {
        if(this.starshipConnection.isConnected()) {
            for(STSdown message : this.starshipConnection.receiveMessagesFromStarship()) {
                if(message.hasConnection()) {
                    this.connectionData.processConnection(message.getConnection());
                }
                if(message.hasLobby()) {
                    this.lobbyData.processLobby(message.getLobby());
                }
                if(message.hasVisualSensors()) {
                    this.visualSensorsData.processVisualSensors(message.getVisualSensors());
                }
            }
        }
    }
    
    public void generateOutput() {
        STSup.Connection stsUpConnection = this.connectionData.generateOutput();
        STSup.Lobby stsUpLobby = this.lobbyData.generateOutput();
        STSup.Piloting stsUpPiloting = this.pilotingData.generateOutput();
        
        if(stsUpConnection != null || stsUpLobby != null || stsUpPiloting != null) {
            STSup.Builder stsUp = STSup.newBuilder();
            if(stsUpConnection != null) {
                stsUp.setConnection(stsUpConnection);
            }
            if(stsUpLobby != null) {
                stsUp.setLobby(stsUpLobby);
            }
            if(stsUpPiloting != null) {
                stsUp.setPiloting(stsUpPiloting);
            }
            this.starshipConnection.sendMessageToStarship(stsUp.build());
        }
    }
    
    public void connectToStarship(String host, Integer port) {
        this.starshipConnection.connect(host, port);
    }
    
    public boolean isConnectedToStarship() {
        return this.starshipConnection.isConnected();
    }

    public boolean getEnteredShipDesigner() {
        return enteredShipDesigner;
    }
    
    public void setEnteredShipDesigner(boolean state) {
        enteredShipDesigner = state;
    }

    public ConnectionData getConnectionData() {
        return connectionData;
    }

    public LobbyData getLobbyData() {
        return lobbyData;
    }

    public PilotingData getPilotingData() {
        return pilotingData;
    }

    public VisualSensorsData getVisualSensorsData() {
        return visualSensorsData;
    }
}
