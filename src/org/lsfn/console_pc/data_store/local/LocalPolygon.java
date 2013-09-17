package org.lsfn.console_pc.data_store.local;

import java.awt.Polygon;

import org.lsfn.console_pc.data_store.sinks.ISinkPolygon;
import org.lsfn.console_pc.data_store.sources.ISourcePolygon;

public class LocalPolygon implements ISinkPolygon, ISourcePolygon {

    private Polygon polygon;
    
    public LocalPolygon() {
        this.polygon = new Polygon();
    }
    
    @Override
    public Polygon getData() {
        // TODO Auto-generated method stub
        return null;
    }

}
