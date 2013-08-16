package org.lsfn.console_pc.screen_management;

import org.lsfn.console_pc.screen_management.ScreenFile.ScreenConfig.OutputLink;

public class ScreenOutputLink {

    private String widgetPath;
    private String dataPath;
    private String type;
    
    public ScreenOutputLink(OutputLink link) {
        this.widgetPath = link.getWidgetPath();
        this.dataPath = link.getOutputPath();
        this.type = link.getType();
    }

    public String getWidgetPath() {
        return widgetPath;
    }

    public String getDataPath() {
        return dataPath;
    }

    public String getType() {
        return type;
    }

}
