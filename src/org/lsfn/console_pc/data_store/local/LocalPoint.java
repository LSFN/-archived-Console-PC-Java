package org.lsfn.console_pc.data_store.local;

import java.awt.Point;

import org.lsfn.console_pc.data_store.sinks.ISinkPoint;
import org.lsfn.console_pc.data_store.sources.ISourcePoint;

public class LocalPoint implements ISinkPoint, ISourcePoint {

    private Point point;
    
    public LocalPoint(Point point) {
        this.point = point;
    }
    
    @Override
    public void setData(Point point) {
        this.point = point;
    }

    @Override
    public Point getData() {
        return this.point;
    }

}
