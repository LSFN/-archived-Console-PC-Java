package org.lsfn.console_pc.input_management;

import java.util.HashMap;
import java.util.Map;

import org.lsfn.console_pc.screen_management.ScreenFile.ScreenConfig;
import org.lsfn.console_pc.screen_management.ScreenFile.ScreenConfig.InputLink.Binding;

public class Bindings {

    private String widgetPath;
    private String inputPath;
    private Map<String, String> controls;
    
    public Bindings(ScreenConfig.InputLink inputLink) {
        this.widgetPath = inputLink.getWidgetPath();
        this.inputPath = inputLink.getInputPath();
        this.controls = new HashMap<String, String>();
        for(Binding binding : inputLink.getBindingsList()) {
            this.controls.put(binding.getControl(), binding.getCommand());
        }
    }

    public String getInputPath() {
        return inputPath;
    }

    public Map<String, String> getControls() {
        return controls;
    }

    public String getWidgetPath() {
        return widgetPath;
    }
    
}
