package org.lsfn.console_pc.widgets;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public interface IWidget {

    public void updateBounds(Rectangle bounds);
    public void drawWidget(Graphics2D g);
    public IWidgetInfo getWidgetInfoForPoint(Point point);
    public int getRatio();
    
}
