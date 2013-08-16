package org.lsfn.console_pc.screen_management;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.lsfn.console_pc.data_management.elements.DataSource;
import org.lsfn.console_pc.input_management.Bindings;
import org.lsfn.console_pc.screen_management.ScreenFile.ScreenConfig;
import org.lsfn.console_pc.screen_management.ScreenFile.ScreenConfig.InputLink;
import org.lsfn.console_pc.screen_management.ScreenFile.ScreenConfig.OutputLink;

import com.google.protobuf.TextFormat;

public class Screen {
    
    private String screenName;
    private Widget widget;
    private Map<String, Bindings> controlMapping;
    private Map<String, ScreenOutputLink> widgetMapping;
    
    protected Screen() {
        
    }
    
    protected Screen(ScreenConfig config) {
        this.screenName = config.getScreenName();
        this.widget = new Widget(config.getWidgetLayout());
        if(config.getInputLinksCount() > 0) {
            this.controlMapping = new HashMap<String, Bindings>();
            for(InputLink link : config.getInputLinksList()) {
                this.controlMapping.put(link.getWidgetPath(), new Bindings(link));
            }
        }
        if(config.getOutputLinksCount() > 0) {
            this.widgetMapping = new HashMap<String, ScreenOutputLink>();
            for(OutputLink link : config.getOutputLinksList()) {
                this.widgetMapping.put(link.getWidgetPath(), new ScreenOutputLink(link));
            }
        }
    }
    
    public static Screen loadScreen(String screenFile) {
        FileReader screenFileReader;
        try {
            System.out.println("Reading file.");
            screenFileReader = new FileReader(screenFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        ScreenConfig.Builder config = ScreenConfig.newBuilder();
        try {
            System.out.println("Making builder.");
            TextFormat.merge(screenFileReader, config);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        System.out.println("Succeeded in doing all of that.");
        return new Screen(config.build());
    }
    
    public String getScreenName() {
        return screenName;
    }

    public void drawScreen(Graphics2D g) {
        this.widget.drawWidget(g);
    }
    
    public void setBounds(Rectangle bounds) {
        this.widget.setBounds(bounds);
    }
    
    public String getWidgetPath(Point p) {
        return this.widget.getWidgetPath(p);
    }

    public Map<String, Bindings> getControlMapping() {
        return controlMapping;
    }

    public Map<String, ScreenOutputLink> getWidgetMapping() {
        return widgetMapping;
    }
    
    public void linkOutput(String path, DataSource dataSource) {
        if(this.widget != null) {
            this.widget.setWidgetData(path, dataSource);
        }
    }
    
}
