package org.lsfn.console_pc.data_store;

import org.lsfn.console_pc.data_store.local.LocalDouble;
import org.lsfn.console_pc.data_store.sinks.ISinkBoolean;
import org.lsfn.console_pc.data_store.sinks.ISinkDouble;
import org.lsfn.console_pc.data_store.sinks.ISinkInteger;
import org.lsfn.console_pc.data_store.sinks.ISinkPoint;
import org.lsfn.console_pc.data_store.sinks.ISinkString;
import org.lsfn.console_pc.data_store.sinks.ISinkStringList;
import org.lsfn.console_pc.data_store.sinks.ISinkTrigger;
import org.lsfn.console_pc.data_store.sinks.SinkDouble;
import org.lsfn.console_pc.data_store.sources.ISourceBoolean;
import org.lsfn.console_pc.data_store.sources.ISourceDouble;
import org.lsfn.console_pc.data_store.sources.ISourceInteger;
import org.lsfn.console_pc.data_store.sources.ISourcePoint;
import org.lsfn.console_pc.data_store.sources.ISourceString;
import org.lsfn.console_pc.data_store.sources.ISourceTrigger;

public class ReactorDataStore implements IDataStore {

	private LocalDouble reactantIntroduction;
	private LocalDouble coolantIntroduction;
	private ISinkDouble reactorOutput;
	private ISinkDouble reactorTemperature;
	
	public ReactorDataStore() {
		reactantIntroduction = new LocalDouble(0);
		coolantIntroduction = new LocalDouble(0);
		reactorOutput = new SinkDouble(0);
		reactorTemperature = new SinkDouble(0);
	}
	
	@Override
	public ISourceBoolean findSourceBoolean(IDataPath dataPath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ISourceString findSourceString(IDataPath dataPath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ISourceInteger findSourceInteger(IDataPath dataPath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ISourceDouble findSourceDouble(IDataPath dataPath) {
		if(dataPath.topLevelMatch("reactantIntroduction")) {
			return this.reactantIntroduction;
		} else if(dataPath.topLevelMatch("coolantIntroduction")) {
			return this.coolantIntroduction;
		}
		return null;
	}

	@Override
	public ISourceTrigger findSourceTrigger(IDataPath dataPath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ISourcePoint findSourcePoint(IDataPath dataPath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ISinkBoolean findSinkBoolean(IDataPath dataPath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ISinkString findSinkString(IDataPath dataPath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ISinkStringList findSinkStringList(IDataPath dataPath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ISinkInteger findSinkInteger(IDataPath dataPath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ISinkDouble findSinkDouble(IDataPath dataPath) {
		if(dataPath.topLevelMatch("reactantIntroduction")) {
			return this.reactantIntroduction;
		} else if(dataPath.topLevelMatch("coolantIntroduction")) {
			return this.coolantIntroduction;
		} else if(dataPath.topLevelMatch("reactorOutput")) {
			return this.reactorOutput;
		} else if(dataPath.topLevelMatch("reactorTemperature")) {
			return this.reactorTemperature;
		}
		return null;
	}

	@Override
	public ISinkTrigger findSinkTrigger(IDataPath dataPath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ISinkPoint findSinkPoint(IDataPath dataPath) {
		// TODO Auto-generated method stub
		return null;
	}

}
