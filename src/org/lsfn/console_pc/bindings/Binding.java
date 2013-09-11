package org.lsfn.console_pc.bindings;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.lsfn.console_pc.data_store.DataPath;
import org.lsfn.console_pc.data_store.IDataPath;
import org.lsfn.console_pc.data_store.IDataStore;
import org.lsfn.console_pc.data_store.sources.ISourceBoolean;
import org.lsfn.console_pc.data_store.sources.ISourceDouble;
import org.lsfn.console_pc.data_store.sources.ISourceInteger;
import org.lsfn.console_pc.data_store.sources.ISourceString;
import org.lsfn.console_pc.input.InputEvent;
import org.lsfn.console_pc.screen.ScreenFile2.ScreenConfig2;
import org.lsfn.console_pc.screen.ScreenFile2.ScreenConfig2.Bindings.Binding.SpecialInput.SpecialInputType;
import org.lsfn.console_pc.widgets.IWidgetPath;
import org.lsfn.console_pc.widgets.WidgetPath;

public class Binding implements IBinding {

    private IWidgetPath lastWidgetClicked;
    private Map<IWidgetPath, ISourceString> printableCharacterInputs;
    private Map<IWidgetPath, ISourceInteger> decimalDigitInputs;
    private Map<Integer, ISourceBoolean> keyStateInputs;
    private Map<IWidgetPath, ISourceDouble> doubleMouseInputs;
    
    public Binding(ScreenConfig2.Bindings bindings, IDataStore dataStore) {
        this.lastWidgetClicked = null;
        this.printableCharacterInputs = new HashMap<IWidgetPath, ISourceString>();
        this.decimalDigitInputs = new HashMap<IWidgetPath, ISourceInteger>();
        this.keyStateInputs = new HashMap<Integer, ISourceBoolean>();
        this.doubleMouseInputs = new HashMap<IWidgetPath, ISourceDouble>();
        
        for(ScreenConfig2.Bindings.Binding binding : bindings.getBindingList()) {
            IDataPath dataPath = new DataPath(binding.getDataPath());
            ISourceBoolean booleanData = dataStore.findSourceBoolean(dataPath);
            ISourceString stringData = dataStore.findSourceString(dataPath);
            ISourceInteger integerData = dataStore.findSourceInteger(dataPath);
            ISourceDouble doubleData = dataStore.findSourceDouble(dataPath);
            if(booleanData != null) {
                if(binding.hasKeyboardInput()) {
                    Integer keyVal = getIntegerValueOfKeyEvent(binding.getKeyboardInput().getKeyName());
                    if(keyVal != null) {
                        this.keyStateInputs.put(keyVal, booleanData);
                    }
                }
            } else if(stringData != null) {
                if(binding.hasSpecialInput() && binding.getSpecialInput().getSpecialInputType() == SpecialInputType.PRINTABLE_CHARACTERS) {
                    this.printableCharacterInputs.put(WidgetPath.fromString(binding.getSpecialInput().getWidgetPath()), stringData);
                }
            } else if(integerData != null) {
                if(binding.hasSpecialInput() && binding.getSpecialInput().getSpecialInputType() == SpecialInputType.DECIMAL_DIGITS) {
                    this.decimalDigitInputs.put(WidgetPath.fromString(binding.getSpecialInput().getWidgetPath()), integerData);
                }
            } else if(doubleData != null) {
                if(binding.hasMouseInput()) {
                    this.doubleMouseInputs.put(WidgetPath.fromString(binding.getSpecialInput().getWidgetPath()), doubleData);
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
            
        }
        
    }
    
    
}
