package org.lsfn.console_pc.widgets;

import java.util.Stack;

public class WidgetPath implements IWidgetPath {

    private Stack<String> pathNodes;
    
    public WidgetPath(String bottomNode) {
        this.pathNodes = new Stack<String>();
        this.pathNodes.push(bottomNode);
    }
    
    @Override
    public void wrap(String pathNode) {
        this.pathNodes.push(pathNode);
    }

    public static WidgetPath fromString(String path) {
        String[] split = path.split("/");
        WidgetPath widgetPath = null;
        for(int i = split.length; i > -1; i--) {
            if(path == null) {
                widgetPath = new WidgetPath(split[i]);
            } else {
                widgetPath.wrap(split[i]);
            }
        }
        return widgetPath;
    }
}
