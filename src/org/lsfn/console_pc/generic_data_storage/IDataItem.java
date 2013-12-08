package org.lsfn.console_pc.generic_data_storage;

import java.util.List;
import java.util.Map;

public interface IDataItem {

	public boolean toBoolean();
	public int toInteger();
	public double toDouble();
	public String toString();
	public List<IDataItem> toList();
	public Map<String, IDataItem> toMap();
	
	public void set(boolean b);
	public void set(int i);
	public void set(double d);
	public void set(String s);
	public void set(List<IDataItem> l);
	public void set(Map<String, IDataItem> m);
}
