package org.lsfn.console_pc.data_store;

import org.lsfn.console_pc.data_store.local.LocalBoolean;
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

public class PowerDistributionDataStore implements IDataStore {

	private LocalBoolean leftEngine;
	private LocalBoolean rightEngine;
	private LocalBoolean forwardLeftThruster;
	private LocalBoolean forwardRightThruster;
	private LocalBoolean rearLeftThruster;
	private LocalBoolean rearRightThruster;
	
	@Override
	public ISourceBoolean findSourceBoolean(IDataPath dataPath) {
		if(dataPath.topLevelMatch("leftEngine")) {
			return this.leftEngine;
		} else if(dataPath.topLevelMatch("rightEngine")) {
			return this.rightEngine;
		} else if(dataPath.topLevelMatch("forwardLeftThruster")) {
			return this.forwardLeftThruster;
		} else if(dataPath.topLevelMatch("forwardRightThruster")) {
			return this.forwardRightThruster;
		} else if(dataPath.topLevelMatch("rearLeftThruster")) {
			return this.rearLeftThruster;
		} else if(dataPath.topLevelMatch("rearRightThruster")) {
			return this.rearRightThruster;
		}
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
		// TODO Auto-generated method stub
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
		if(dataPath.topLevelMatch("leftEngine")) {
			return this.leftEngine;
		} else if(dataPath.topLevelMatch("rightEngine")) {
			return this.rightEngine;
		} else if(dataPath.topLevelMatch("forwardLeftThruster")) {
			return this.forwardLeftThruster;
		} else if(dataPath.topLevelMatch("forwardRightThruster")) {
			return this.forwardRightThruster;
		} else if(dataPath.topLevelMatch("rearLeftThruster")) {
			return this.rearLeftThruster;
		} else if(dataPath.topLevelMatch("rearRightThruster")) {
			return this.rearRightThruster;
		}
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
		// TODO Auto-generated method stub
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
