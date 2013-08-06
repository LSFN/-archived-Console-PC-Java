package org.lsfn.console_pc;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.lsfn.console_pc.ScreenFile.ScreenConfig;
import org.lsfn.console_pc.ScreenFile.ScreenConfig.InputLink;
import org.lsfn.console_pc.ScreenFile.ScreenConfig.OutputLink;

import com.google.protobuf.TextFormat;

public class Screen {
    
    private String screenName;
    private Widget widget;
    private Map<String, String> controlMapping;
    private Map<String, String> widgetMapping;
    
    protected Screen() {
        
    }
    
    protected Screen(ScreenConfig config) {
        this.screenName = config.getScreenName();
        this.widget = new Widget(config.getWidgetLayout());
        if(config.getInputLinksCount() > 0) {
            controlMapping = new HashMap<String, String>();
            for(InputLink link : config.getInputLinksList()) {
                controlMapping.put(link.getControlPath(), link.getInputPath());
            }
        }
        if(config.getOutputLinksCount() > 0) {
            widgetMapping = new HashMap<String, String>();
            for(OutputLink link : config.getOutputLinksList()) {
                widgetMapping.put(link.getWidgetPath(), link.getOutputPath());
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
    
    public void getWidgetPath(Point p) {
        this.widget.getWidgetPath(p);
    }
}
