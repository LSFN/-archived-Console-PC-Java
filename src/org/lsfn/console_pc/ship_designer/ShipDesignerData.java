package org.lsfn.console_pc.ship_designer;

import java.awt.Component;

import org.lsfn.console_pc.data_management.elements.ControlledData;
import org.lsfn.console_pc.data_management.elements.DataSource;
import org.lsfn.console_pc.data_management.elements.UpdatableBoolean;

public class ShipDesignerData {

    private UpdatableBoolean active;
    private ShipDesigner shipDesigner;
    
    public ShipDesignerData(Component parent) {
        this.active = new UpdatableBoolean(false);
        this.shipDesigner = new ShipDesigner(parent);
    }
    
    public boolean isActive() {
        return this.active.getData();
    }
    
    public DataSource getDataSourceFromPath(String dataPath) {
        if(dataPath.equals("shipDesigner")) {
            return this.shipDesigner;
        }
        return null;
    }

    public ControlledData getControlledDataFromPath(String dataPath) {
        if(dataPath.equals("active")) {
            return this.active;
        } else if(dataPath.equals("shipDesigner")) {
            return this.shipDesigner;
        }
        return null;
    }

}
