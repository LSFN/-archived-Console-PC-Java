package org.lsfn.console_pc.data_store;

import org.lsfn.console_pc.STS.STSdown;
import org.lsfn.console_pc.STS.STSup;
import org.lsfn.console_pc.STS.STSup.Lobby;
import org.lsfn.console_pc.data_store.local.LocalString;
import org.lsfn.console_pc.data_store.local.LocalTrigger;
import org.lsfn.console_pc.data_store.sinks.ISinkBoolean;
import org.lsfn.console_pc.data_store.sinks.ISinkDouble;
import org.lsfn.console_pc.data_store.sinks.ISinkInteger;
import org.lsfn.console_pc.data_store.sinks.ISinkPoint;
import org.lsfn.console_pc.data_store.sinks.ISinkString;
import org.lsfn.console_pc.data_store.sinks.ISinkStringList;
import org.lsfn.console_pc.data_store.sinks.ISinkTrigger;
import org.lsfn.console_pc.data_store.sinks.SinkBoolean;
import org.lsfn.console_pc.data_store.sinks.SinkString;
import org.lsfn.console_pc.data_store.sinks.SinkStringList;
import org.lsfn.console_pc.data_store.sources.ISourceBoolean;
import org.lsfn.console_pc.data_store.sources.ISourceDouble;
import org.lsfn.console_pc.data_store.sources.ISourceInteger;
import org.lsfn.console_pc.data_store.sources.ISourcePoint;
import org.lsfn.console_pc.data_store.sources.ISourceString;
import org.lsfn.console_pc.data_store.sources.ISourceTrigger;

public class LobbyDataStore implements IDataStore {

	private LocalString editShipName;
	private SinkString recordedShipName;
	private LocalTrigger changeShipName;
	private SinkBoolean ready;
	private LocalTrigger readyUp;
	private SinkBoolean gameStarted;
	private SinkStringList shipList;
	
	public LobbyDataStore() {
		this.editShipName = new LocalString("Mungle box");
		this.recordedShipName = new SinkString("Mungle box");
		this.changeShipName = new LocalTrigger();
		this.ready = new SinkBoolean(false);
		this.readyUp = new LocalTrigger();
		this.gameStarted = new SinkBoolean(false);
		this.shipList = new SinkStringList();
	}
	
	public void processInput(STSdown.Lobby lobby) {
		if(lobby.hasShipName()) {
			this.recordedShipName.setData(lobby.getShipName());
		}
		if(lobby.hasReadyState()) {
			this.ready.setData(lobby.getReadyState());
		}
		if(lobby.hasGameStarted()) {
			this.gameStarted.setData(lobby.getGameStarted());
		}
		if(lobby.getShipsInGameCount() > 0) {
			this.shipList.setData(lobby.getShipsInGameList());
		}
	}
	
	public Lobby generateOutput() {
		boolean shipNameUpdate = this.changeShipName.isTriggered();
		boolean readyUpUpdate = this.readyUp.isTriggered();
		if(shipNameUpdate || readyUpUpdate) {
			STSup.Lobby.Builder message = STSup.Lobby.newBuilder();
			if(readyUpUpdate) {
				message.setReadyState(!ready.getData());
			}
			if(shipNameUpdate) {
				message.setShipName(editShipName.getData());
			}
			return message.build();
		}
		return null;
	}
	
	@Override
	public ISourceBoolean findSourceBoolean(IDataPath dataPath) {
		return null;
	}

	@Override
	public ISourceString findSourceString(IDataPath dataPath) {
		if(dataPath.topLevelMatch("editShipName")) {
			return editShipName;
		}
		return null;
	}

	@Override
	public ISourceInteger findSourceInteger(IDataPath dataPath) {
		return null;
	}

	@Override
	public ISourceDouble findSourceDouble(IDataPath dataPath) {
		return null;
	}

	@Override
	public ISourceTrigger findSourceTrigger(IDataPath dataPath) {
		if(dataPath.topLevelMatch("readyUp")) {
			return readyUp;
		} else if(dataPath.topLevelMatch("changeShipName")) {
			return changeShipName;
		}
		return null;
	}

	@Override
	public ISourcePoint findSourcePoint(IDataPath dataPath) {
		return null;
	}

	@Override
	public ISinkBoolean findSinkBoolean(IDataPath dataPath) {
		if(dataPath.topLevelMatch("ready")) {
			return ready;
		} else if(dataPath.topLevelMatch("gameStarted")) {
			return gameStarted;
		}
		return null;
	}

	@Override
	public ISinkString findSinkString(IDataPath dataPath) {
		if(dataPath.topLevelMatch("editShipName")) {
			return editShipName;
		} else if(dataPath.topLevelMatch("recordedShipName")) {
			return recordedShipName;
		}
		return null;
	}

	@Override
	public ISinkStringList findSinkStringList(IDataPath dataPath) {
		if(dataPath.topLevelMatch("shipList")) {
			return shipList;
		}
		return null;
	}
	
	@Override
	public ISinkInteger findSinkInteger(IDataPath dataPath) {
		return null;
	}

	@Override
	public ISinkDouble findSinkDouble(IDataPath dataPath) {
		return null;
	}

	@Override
	public ISinkTrigger findSinkTrigger(IDataPath dataPath) {
		if(dataPath.topLevelMatch("readyUp")) {
			return readyUp;
		} else if(dataPath.topLevelMatch("changeShipName")) {
			return changeShipName;
		}
		return null;
	}


	@Override
	public ISinkPoint findSinkPoint(IDataPath dataPath) {
		return null;
	}

}
