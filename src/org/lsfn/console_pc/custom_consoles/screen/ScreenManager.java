package org.lsfn.console_pc.custom_consoles.screen;

import java.util.HashMap;
import java.util.Map;

import org.lsfn.console_pc.custom_consoles.bindings.IBinding;
import org.lsfn.console_pc.custom_consoles.screen.ScreenFile.ScreenConfig;
import org.lsfn.console_pc.custom_consoles.screen.ScreenFile.ScreenConfig.WidgetLayout.WidgetType;
import org.lsfn.console_pc.custom_consoles.widgets.IWidget;
import org.lsfn.console_pc.custom_consoles.widgets.RectangularTextWidget;
import org.lsfn.console_pc.data_store.IDataStore;

public class ScreenManager {

    /**
     * 
     */
    private static final long serialVersionUID = -577908966291529643L;
    
    private IDataStore dataStore;
    private Map<String, IWidget> widgetLayouts;
    private Map<String, IBinding> bindings;

    public ScreenManager(IDataStore dataStore) {
        this.widgetLayouts = new HashMap<String, IWidget>();
        this.dataStore = dataStore;
    }
    
    private void loadScreens() {
    	
    }
    
    public void registerWidgetLayout(String name, ScreenConfig.WidgetLayout widgetLayout) {
        if(widgetLayout.getWidgetType() == WidgetType.RECTANGULAR_TEXT) {
            this.widgetLayouts.put(name, new RectangularTextWidget(widgetLayout, this.dataStore));
        }
    }
}
