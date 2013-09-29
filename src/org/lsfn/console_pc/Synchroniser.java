package org.lsfn.console_pc;

import org.lsfn.console_pc.STS.STSdown;
import org.lsfn.console_pc.data_store.DataPath;
import org.lsfn.console_pc.data_store.DataStore;
import org.lsfn.console_pc.data_store.sinks.ISinkInteger;
import org.lsfn.console_pc.data_store.sinks.ISinkString;
import org.lsfn.console_pc.data_store.sinks.ISinkTrigger;

/**
 * This class will aim to upload source data and download sink data from the Starship
 * This class will also act on certain triggers to form or sever connections
 * 
 * @author LukeusMaximus
 *
 */
public class Synchroniser extends Thread {
	
	private DataStore dataStore;
	private StarshipConnection starshipConnection;
	private ISinkTrigger connectStarship;
	private ISinkTrigger disconnectStarship;
	private ISinkString starshipHostnameSink;
	private ISinkInteger starshipPortSink;
	private boolean running;
	
	public Synchroniser(DataStore dataStore) {
		this.dataStore = dataStore;
		this.starshipConnection = new StarshipConnection();
		this.connectStarship = this.dataStore.findSinkTrigger(new DataPath("starshipConnection/connect"));
		this.disconnectStarship = this.dataStore.findSinkTrigger(new DataPath("starshipConnection/disconnect"));
		this.starshipHostnameSink = this.dataStore.findSinkString(new DataPath("starshipConnection/hostname"));
		this.starshipPortSink = this.dataStore.findSinkInteger(new DataPath("starshipConnection/port"));
	}
	
	@Override
	public void run() {
		if(this.starshipConnection.isConnected()) {
			// Receive data and forward to sinks
			for(STSdown message : this.starshipConnection.receiveMessagesFromStarship()) {
				this.dataStore.processInput(message);
			}
			// Forward data from sinks to Starship
			this.starshipConnection.sendMessageToStarship(this.dataStore.generateOutput());
		}
		
		// Process special triggers
		if(this.connectStarship.isTriggered() && !this.starshipConnection.isConnected()) {
			this.starshipConnection.connect(starshipHostnameSink.getData(), starshipPortSink.getData());
		} else if(this.disconnectStarship.isTriggered() && this.starshipConnection.isConnected()) {
			this.starshipConnection.disconnect();
		}
	}
}
