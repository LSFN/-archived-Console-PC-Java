package org.lsfn.console_pc.data_management;

import org.lsfn.console_pc.data_management.elements.ControlledData;
import org.lsfn.console_pc.data_management.elements.DataSource;
import org.lsfn.console_pc.data_management.elements.TypeableInteger;
import org.lsfn.console_pc.data_management.elements.TypeableString;

public class ConsoleData {

    private TypeableString hostname;
    private TypeableInteger port;
    
    public ConsoleData() {
        this.hostname = new TypeableString("localhost");
        this.port = new TypeableInteger(39460);
    }

    public DataSource getDataSourceFromPath(String dataPath) {
        if(dataPath.equals("hostname")) {
            return this.hostname;
        } else if(dataPath.equals("port")) {
            return this.port;
        }
        return null;
    }

    public ControlledData getControlledDataFromPath(String dataPath) {
        if(dataPath.equals("hostname")) {
            return this.hostname;
        } else if(dataPath.equals("port")) {
            return this.port;
        }
        return null;
    }
    
}
