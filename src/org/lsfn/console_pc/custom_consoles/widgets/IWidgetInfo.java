package org.lsfn.console_pc.custom_consoles.widgets;

import java.awt.Point;

public interface IWidgetInfo {

    public IWidgetPath getWidgetPath();
    public void wrapWidgetPath(String pathNode);
    public Double getDoubleData();
    public Point getPointData();
}
