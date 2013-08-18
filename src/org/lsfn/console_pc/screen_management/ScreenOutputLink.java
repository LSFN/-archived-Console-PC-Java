package org.lsfn.console_pc.screen_management;

import org.lsfn.console_pc.screen_management.ScreenFile.ScreenConfig.OutputLink;

public class ScreenOutputLink {

    private String widgetPath;
    private String dataPath;
    
    public ScreenOutputLink(OutputLink link) {
        this.widgetPath = link.getWidgetPath();
        this.dataPath = link.getDataPath();
    }

    public String getWidgetPath() {
        return widgetPath;
    }

    public String getDataPath() {
        return dataPath;
    }

}
