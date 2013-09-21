package org.lsfn.console_pc.custom_consoles.screen;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import org.lsfn.console_pc.custom_consoles.screen.ScreenFile.ScreenConfig;
import org.lsfn.console_pc.custom_consoles.screen.ScreenFile.ScreenConfig.WidgetLayout.WidgetType;
import org.lsfn.console_pc.custom_consoles.widgets.IWidget;
import org.lsfn.console_pc.custom_consoles.widgets.RectangularTextWidget;
import org.lsfn.console_pc.data_store.IDataStore;

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
    
    public void registerWidgetLayout(String name, ScreenConfig.WidgetLayout widgetLayout) {
        if(widgetLayout.getWidgetType() == WidgetType.RECTANGULAR_TEXT) {
            this.widgetLayouts.put(name, new RectangularTextWidget(widgetLayout, this.dataStore));
        }
    }
}
