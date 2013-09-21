package org.lsfn.console_pc.custom_consoles.widgets;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import org.lsfn.console_pc.custom_consoles.screen.ScreenFile.ScreenConfig.WidgetLayout;

public interface IWidgetManager {

    public void updateBounds(Rectangle bounds);
    public void drawWidgets(Graphics2D g);
    public IWidgetInfo getWidgetInfoForPoint(Point point);
    public void registerWidgetLayout(String name, WidgetLayout widgetLayout);
}
