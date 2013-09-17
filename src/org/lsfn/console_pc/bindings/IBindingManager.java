package org.lsfn.console_pc.bindings;

import org.lsfn.console_pc.input.InputEvent;
import org.lsfn.console_pc.screen.ScreenFile.ScreenConfig;

public interface IBindingManager {

    public void input(InputEvent inputEvent);
    public void registerBindings(String name, ScreenConfig.Bindings bindings);
}
