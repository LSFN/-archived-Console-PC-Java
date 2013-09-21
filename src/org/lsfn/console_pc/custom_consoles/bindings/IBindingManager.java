package org.lsfn.console_pc.custom_consoles.bindings;

import org.lsfn.console_pc.custom_consoles.input.InputEvent;
import org.lsfn.console_pc.custom_consoles.screen.ScreenFile.ScreenConfig;

public interface IBindingManager {

    public void input(InputEvent inputEvent);
    public void registerBindings(String name, ScreenConfig.Bindings bindings);
}
