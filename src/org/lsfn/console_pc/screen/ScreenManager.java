package org.lsfn.console_pc.screen;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import org.lsfn.console_pc.data_store.IDataStore;
import org.lsfn.console_pc.screen.ScreenFile2.ScreenConfig2;
import org.lsfn.console_pc.screen.ScreenFile2.ScreenConfig2.WidgetLayout.WidgetType;
import org.lsfn.console_pc.widgets.IWidget;
import org.lsfn.console_pc.widgets.RectangularTextWidget;

public class ScreenManager extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = -577908966291529643L;
    
    private IDataStore dataStore;
    private Map<String, IWidget> widgetLayouts;

    public ScreenManager(IDataStore dataStore) {
        this.widgetLayouts = new HashMap<String, IWidget>();
        this.dataStore = dataStore;
    }
    
    public void registerWidgetLayout(String name, ScreenConfig2.WidgetLayout widgetLayout) {
        if(widgetLayout.getWidgetType() == WidgetType.RECTANGULAR_TEXT) {
            this.widgetLayouts.put(name, new RectangularTextWidget(widgetLayout, this.dataStore));
        }
    }
}
