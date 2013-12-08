package org.lsfn.console_pc.generic_data_storage;

import java.util.List;
import java.util.Map;

/**
 * This class is for storing data in a non-rigid manner kind of like JSON.
 * It is now your obligation to question my sanity in doing this.
 * @author LukeusMaximus
 *
 */
public class DataItem implements IDataItem {
	
	private boolean booleanData;
	private int integerData;
	private double doubleData;
	private String stringData;
	private List<IDataItem> listData;
	private Map<String, IDataItem> mapData;
		
	public DataItem(boolean b) {
		setDefaults();
		booleanData = b;
	}
	
	public DataItem(int i) {
		setDefaults();
		integerData = i;
	}
	
	public DataItem(double d) {
		setDefaults();
		doubleData = d;
	}
	
	public DataItem(String s) {
		setDefaults();
		stringData = s;
	}
	
	public DataItem(List<IDataItem> l) {
		setDefaults();
		listData = l;
	}
	
	public DataItem(Map<String, IDataItem> m) {
		setDefaults();
		mapData = m;
	}
	
	private void setDefaults() {
		booleanData = false;
		integerData = 0;
		doubleData = 0;
		stringData = null;
		listData = null;
		mapData = null;
	}
	
	@Override
	public boolean toBoolean() {
		return booleanData;
	}
	
	@Override
	public int toInteger() {
		return integerData;
	}
	
	@Override
	public double toDouble() {
		return doubleData;
	}
	
	@Override
	public String toString() {
		return stringData;
	}
	
	@Override
	public List<IDataItem> toList() {
		return listData;
	}

	@Override
	public Map<String, IDataItem> toMap() {
		return mapData;
	}
	
	@Override
	public void set(boolean b) {
		booleanData = b;
	}
	
	@Override
	public void set(int i) {
		integerData = i;
	}
	
	@Override
	public void set(double d) {
		doubleData = d;
	}
	
	@Override
	public void set(String s) {
		stringData = s;
	}

	@Override
	public void set(List<IDataItem> l) {
		listData = l;
	}

	@Override
	public void set(Map<String, IDataItem> m) {
		mapData = m;
	}

}
