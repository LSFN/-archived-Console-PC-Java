package org.lsfn.console_pc.input_management;

import org.lsfn.console_pc.screen_management.ScreenFile.ScreenConfig;

public class ControlInputLink {

    private String widgetPath;
    private String dataPath;
    
    public ControlInputLink(ScreenConfig.InputLink inputLink) {
        this.widgetPath = inputLink.getWidgetPath();
        this.dataPath = inputLink.getDataPath();
    }

    public String getDataPath() {
        return dataPath;
    }

    public String getWidgetPath() {
        return widgetPath;
    }
    
}
