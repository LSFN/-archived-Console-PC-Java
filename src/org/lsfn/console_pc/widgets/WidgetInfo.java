package org.lsfn.console_pc.widgets;

import java.awt.Point;

public class WidgetInfo implements IWidgetInfo {

    private IWidgetPath widgetPath;
    private Double doubleData;
    private Point pointData;
    
    public WidgetInfo(String bottomNode, Double doubleData, Point pointData) {
        this.widgetPath = new WidgetPath(bottomNode);
        this.doubleData = doubleData;
        this.pointData = pointData;
    }

    @Override
    public void wrapWidgetPath(String pathNode) {
        this.widgetPath.wrap(pathNode);        
    }

    @Override
    public IWidgetPath getWidgetPath() {
        return this.widgetPath;
    }

    @Override
    public Double getDoubleData() {
        return this.doubleData;
    }

    @Override
    public Point getPointData() {
        return this.pointData;
    }

}
