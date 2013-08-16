package org.lsfn.console_pc.data_management;

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

    public DataSource getDataSourceFromPath(String path) {
        if(path.equals("hostname")) {
            return this.hostname;
        } else if(path.equals("port")) {
            return this.port;
        }
        return null;
    }
    
}
