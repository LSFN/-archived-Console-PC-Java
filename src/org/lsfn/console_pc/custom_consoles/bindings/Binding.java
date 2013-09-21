package org.lsfn.console_pc.custom_consoles.bindings;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.lsfn.console_pc.custom_consoles.input.InputEvent;
import org.lsfn.console_pc.custom_consoles.screen.ScreenFile.ScreenConfig;
import org.lsfn.console_pc.custom_consoles.screen.ScreenFile.ScreenConfig.Bindings.Binding.SpecialInput.SpecialInputType;
import org.lsfn.console_pc.custom_consoles.widgets.IWidgetPath;
import org.lsfn.console_pc.custom_consoles.widgets.WidgetPath;
import org.lsfn.console_pc.data_store.DataPath;
import org.lsfn.console_pc.data_store.IDataPath;
import org.lsfn.console_pc.data_store.IDataStore;
import org.lsfn.console_pc.data_store.sources.ISourceBoolean;
import org.lsfn.console_pc.data_store.sources.ISourceDouble;
import org.lsfn.console_pc.data_store.sources.ISourceInteger;
import org.lsfn.console_pc.data_store.sources.ISourcePoint;
import org.lsfn.console_pc.data_store.sources.ISourceString;
import org.lsfn.console_pc.data_store.sources.ISourceTrigger;

public class Binding implements IBinding {

    private IWidgetPath lastWidgetClicked;
    // Mouse inputs
    private Map<IWidgetPath, ISourceDouble> doubleMouseInputs;
    private Map<IWidgetPath, ISourceTrigger> mouseTriggerInputs;
    private Map<IWidgetPath, ISourcePoint> mousePointInputs;
    // Keyboard inputs (integer represents keycode)
    private Map<IWidgetPath, ISourceString> printableCharacterInputs;
    private Map<IWidgetPath, ISourceInteger> decimalDigitInputs;
    private Map<Integer, ISourceBoolean> keyStateInputs;
    private Map<Integer, ISourceTrigger> keyTriggerInputs;
    
    public Binding(ScreenConfig.Bindings bindings, IDataStore dataStore) {
        this.lastWidgetClicked = null;
        
        this.doubleMouseInputs = new HashMap<IWidgetPath, ISourceDouble>();
        this.mouseTriggerInputs = new HashMap<IWidgetPath, ISourceTrigger>();
        this.mousePointInputs = new HashMap<IWidgetPath, ISourcePoint>();

        this.printableCharacterInputs = new HashMap<IWidgetPath, ISourceString>();
        this.decimalDigitInputs = new HashMap<IWidgetPath, ISourceInteger>();
        this.keyStateInputs = new HashMap<Integer, ISourceBoolean>();
        this.keyTriggerInputs = new HashMap<Integer, ISourceTrigger>();
        
        for(ScreenConfig.Bindings.Binding binding : bindings.getBindingList()) {
            IDataPath dataPath = new DataPath(binding.getDataPath());
            ISourceBoolean booleanSource = dataStore.findSourceBoolean(dataPath);
            ISourceString stringSource = dataStore.findSourceString(dataPath);
            ISourceInteger integerSource = dataStore.findSourceInteger(dataPath);
            ISourceDouble doubleSource = dataStore.findSourceDouble(dataPath);
            ISourceTrigger triggerSource = dataStore.findSourceTrigger(dataPath);
            ISourcePoint pointSource = dataStore.findSourcePoint(dataPath);
            
            if(booleanSource != null) {
                if(binding.hasKeyboardInput()) {
                    Integer keyVal = getIntegerValueOfKeyEvent(binding.getKeyboardInput().getKeyName());
                    if(keyVal != null) {
                        this.keyStateInputs.put(keyVal, booleanSource);
                    }
                }
            } else if(stringSource != null) {
                if(binding.hasSpecialInput() && binding.getSpecialInput().getSpecialInputType() == SpecialInputType.PRINTABLE_CHARACTERS) {
                    this.printableCharacterInputs.put(WidgetPath.fromString(binding.getSpecialInput().getWidgetPath()), stringSource);
                }
            } else if(integerSource != null) {
                if(binding.hasSpecialInput() && binding.getSpecialInput().getSpecialInputType() == SpecialInputType.DECIMAL_DIGITS) {
                    this.decimalDigitInputs.put(WidgetPath.fromString(binding.getSpecialInput().getWidgetPath()), integerSource);
                }
            } else if(doubleSource != null) {
                if(binding.hasMouseInput()) {
                    this.doubleMouseInputs.put(WidgetPath.fromString(binding.getMouseInput().getWidgetPath()), doubleSource);
                }
            } else if(triggerSource != null) {
                if(binding.hasMouseInput()) {
                    this.mouseTriggerInputs.put(WidgetPath.fromString(binding.getMouseInput().getWidgetPath()), triggerSource);
                }
                if(binding.hasKeyboardInput()) {
                    Integer keyVal = getIntegerValueOfKeyEvent(binding.getKeyboardInput().getKeyName());
                    if(keyVal != null) {
                        this.keyTriggerInputs.put(keyVal, triggerSource);
                    }
                }
            } else if(pointSource != null) {
                if(binding.hasMouseInput()) {
                    this.mousePointInputs.put(WidgetPath.fromString(binding.getMouseInput().getWidgetPath()), pointSource);
                }
            }
        }
    }
    
    private Integer getIntegerValueOfKeyEvent(String name) {
        Class<KeyEvent> c = KeyEvent.class;
        Field f = null;
        Integer i = null;
        try {
            f = c.getDeclaredField(name);
            i = f.getInt(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return i;
    }
    
    @Override
    public void input(InputEvent inputEvent) {
        // Change the lastWidgetClicked if it needs to be.
        if(inputEvent.getWidgetInfo() != null) {
            this.lastWidgetClicked = inputEvent.getWidgetInfo().getWidgetPath();
        }
        if(inputEvent.getKeyEvent() != null) {
            KeyEvent keyEvent = inputEvent.getKeyEvent();
            if(inputEvent.getPressedState() && this.printableCharacterInputs.containsKey(this.lastWidgetClicked)) {
                ISourceString sourceString = this.printableCharacterInputs.get(this.lastWidgetClicked);
                int charValue = (int)keyEvent.getKeyChar();
                if(charValue >= (int)' ' && charValue <= (int)'~') {
                    sourceString.appendCharacter(keyEvent.getKeyChar());
                } else if(keyEvent.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    sourceString.deleteCharacter();
                }
            } else if(inputEvent.getPressedState() && this.decimalDigitInputs.containsKey(this.lastWidgetClicked)) {
                ISourceInteger sourceInteger = this.decimalDigitInputs.get(this.lastWidgetClicked);
                int charValue = (int)keyEvent.getKeyChar();
                if(charValue >= (int)'0' && charValue <= (int)'9') {
                    sourceInteger.addTypedDigit(keyEvent.getKeyChar());
                } else if(keyEvent.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    sourceInteger.deleteTypedDigit();
                }
            } else if(this.keyStateInputs.containsKey(keyEvent.getKeyCode())) {
                ISourceBoolean sourceBoolean = this.keyStateInputs.get(keyEvent.getKeyCode());
                sourceBoolean.setBoolean(inputEvent.getPressedState());
            }
        }
        if(inputEvent.getMouseEvent() != null) {
            if(inputEvent.getPressedState() && this.doubleMouseInputs.containsKey(this.lastWidgetClicked)) {
                ISourceDouble sourceDouble = this.doubleMouseInputs.get(this.lastWidgetClicked);
                sourceDouble.setData(inputEvent.getWidgetInfo().getDoubleData());
            }
            if(inputEvent.getPressedState() && this.mousePointInputs.containsKey(this.lastWidgetClicked)) {
                ISourcePoint sourcePoint = this.mousePointInputs.get(this.lastWidgetClicked);
                sourcePoint.setData(inputEvent.getWidgetInfo().getPointData());
            }
            if(inputEvent.getPressedState() && this.mouseTriggerInputs.containsKey(this.lastWidgetClicked)) {
                ISourceTrigger sourceTrigger = this.mouseTriggerInputs.get(this.lastWidgetClicked);
                sourceTrigger.trigger();
            }
        }
        
    }
    
    
}
