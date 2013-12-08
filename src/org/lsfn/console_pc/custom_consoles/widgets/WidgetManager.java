package org.lsfn.console_pc.custom_consoles.widgets;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Map;

import org.lsfn.console_pc.custom_consoles.screen.ScreenFile.ScreenConfig;
import org.lsfn.console_pc.custom_consoles.screen.ScreenFile.ScreenConfig.WidgetLayout.WidgetType;
import org.lsfn.console_pc.data_store.IDataStore;

public class WidgetManager implements IWidgetManager {

    private IWidget topLevelWidget;
    private IDataStore dataStore;
    private Map<String, IWidget> widgetLayouts;
    
    public WidgetManager(IDataStore dataStore) {
        this.topLevelWidget = null;
    }
    
    @Override
    public void updateBounds(Rectangle bounds) {
        this.topLevelWidget.updateBounds(bounds);
    }

    @Override
    public void drawWidgets(Graphics2D g) {
        this.topLevelWidget.drawWidget(g);
    }

    @Override
    public IWidgetInfo getWidgetInfoForPoint(Point point) {
        return topLevelWidget.getWidgetInfoForPoint(point);
    }
    
    @Override
    public void registerWidgetLayout(String name, ScreenConfig.WidgetLayout widgetLayout) {
        if(widgetLayout.getWidgetType() == WidgetType.RECTANGULAR_TEXT) {
            this.widgetLayouts.put(name, new RectangularTextWidget(widgetLayout, this.dataStore));
        }
    }

	@Override
	public void setCurrentWidgetLayout(String name) {
		this.topLevelWidget = this.widgetLayouts.get(name);
	}

}
