package org.lsfn.console_pc.data_store;

public interface IDataPath {
    
    public boolean topLevelMatch(Object obj);
    public IDataPath stripTopLevel();
}
