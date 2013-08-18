package org.lsfn.console_pc.data_management;

import java.awt.Point;
import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;

import org.lsfn.console_pc.STS.STSdown;
import org.lsfn.console_pc.STS.STSup;
import org.lsfn.console_pc.data_management.elements.ControlledData;
import org.lsfn.console_pc.data_management.elements.DataSource;
import org.lsfn.console_pc.input_management.InputManager;
import org.lsfn.console_pc.screen_management.Screen;
import org.lsfn.console_pc.screen_management.ScreenManager;
import org.lsfn.console_pc.screen_management.ScreenOutputLink;

public class DataManager extends Thread {

    private static final String consolePrefix = "console/";
    private static final String connectionPrefix = "connection/";
    private static final String lobbyPrefix = "lobby/";
    private static final String pilotingPrefix = "piloting/";
    private static final String visualSensorsPrefix = "visual_sensors/";
    
    private InputManager inputManager;
    private ScreenManager screenManager;
    
    //private StarshipConnection starshipConnection;
    
    //private ConsoleData consoleData;
    private StarshipConnectionData starshipConnectionData;
    private ConnectionData connectionData;
    private LobbyData lobbyData;
    private PilotingData pilotingData;
    private VisualSensorsData visualSensorsData;
    
    private boolean running;
    
    public DataManager() {
        this.inputManager = new InputManager(this);
        this.screenManager = new ScreenManager();
        this.screenManager.addMouseListener(this.inputManager);
        
        //this.starshipConnection = new StarshipConnection();
        
        //this.consoleData = new ConsoleData();
        this.starshipConnectionData = new StarshipConnectionData();
        this.connectionData = new ConnectionData();
        this.lobbyData = new LobbyData();
        this.pilotingData = new PilotingData();
        this.visualSensorsData = new VisualSensorsData();
    }
    
    private void setupScreens() {
        Map<String, Screen> screens = new HashMap<String, Screen>();
        File screenFolder = new File("screens/");
        String[] screenFiles = screenFolder.list(new FilenameFilter() {
            @Override
            public boolean accept(File arg0, String arg1) {
                return arg1.endsWith(".screen");
            }
        });
        for(int i = 0; i < screenFiles.length; i++) {
            System.out.println("File name: " + screenFiles[i]);
            Screen loadedScreen = Screen.loadScreen("screens/" + screenFiles[i]);
            if(loadedScreen != null) {
                for(ScreenOutputLink link : loadedScreen.getWidgetMapping().values()) {
                    // Find the data source in the DataManager
                    DataSource dataSource = findDataSource(link.getDataPath());
                    // Give the widget the pointer to later get the data from
                    loadedScreen.linkOutput(link.getWidgetPath(), dataSource);
                }
                System.out.println("Loaded screen " + loadedScreen.getScreenName());
                screens.put(loadedScreen.getScreenName(), loadedScreen);
            }
        }
        this.screenManager.setScreens(screens);
        this.screenManager.makeCurrentScreen("Menu");
    }
    
    public InputManager getInputManager() {
        return inputManager;
    }

    public ScreenManager getScreenManager() {
        return screenManager;
    }

    private DataSource findDataSource(String dataPath) {
        if(dataPath.startsWith(consolePrefix)) {
            return this.starshipConnectionData.getDataSourceFromPath(dataPath.substring(consolePrefix.length()));
        }
        return null;
    }
    
    public String getWidgetPointedToPath(Point point) {
        return this.screenManager.getWidgetPath(point);
    }
    
    public ControlledData findControlledData(String widgetPath) {
        String dataPath = this.screenManager.getDataPathForWidgetPath(widgetPath);
        if(dataPath.startsWith(consolePrefix)) {
            return this.starshipConnectionData.getControlledDataFromPath(dataPath.substring(consolePrefix.length()));
        }
        return null;
    }
    
    private void processInput() {
        if(this.starshipConnectionData.isConnected()) {
            for(STSdown message : this.starshipConnectionData.receiveMessagesFromStarship()) {
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
    
    private void generateOutput() {
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
            this.starshipConnectionData.sendMessageToStarship(stsUp.build());
        }
    }
    
    private void checkScreenChangeConditions() {
        if(this.starshipConnectionData.isConnected()) {
            this.screenManager.makeCurrentScreen("Lobby");
        } else {
            this.screenManager.makeCurrentScreen("Menu");
        }
    }
    
    @Override
    public void run() {
        setupScreens();        
        this.running = true;
        while(this.running) {
            // Process the input from the Starship (if connected)
            processInput();
            // Paint the screen
            this.screenManager.repaint();
            // Send any messages that need to be sent to the Starship
            generateOutput();
        }
    }

    
}
