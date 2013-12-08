package org.lsfn.console_pc.custom_consoles;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.HierarchyEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JFrame;

import org.lsfn.console_pc.StarshipConnection;
import org.lsfn.console_pc.custom_consoles.bindings.BindingManager;
import org.lsfn.console_pc.custom_consoles.input.InputManager;
import org.lsfn.console_pc.custom_consoles.screen.ScreenFile;
import org.lsfn.console_pc.custom_consoles.screen.ScreenManager;
import org.lsfn.console_pc.custom_consoles.screen.ScreenFile.ScreenConfig;
import org.lsfn.console_pc.custom_consoles.widgets.WidgetManager;
import org.lsfn.console_pc.data_store.DataStore;
import org.lsfn.console_pc.specialised_display.ISpecialisedDisplay;

import com.google.protobuf.TextFormat;

public class ConsoleManager implements ISpecialisedDisplay {

    private DataStore dataStore;
    private WidgetManager widgetManager;
    private BindingManager bindingManager;
    private InputManager inputManager;
	private FilenameFilter screenFilenameFilter;
    
    public ConsoleManager(DataStore dataStore, Rectangle bounds) {
        this.dataStore = dataStore;
        this.widgetManager = new WidgetManager(this.dataStore);
        this.bindingManager = new BindingManager(this.dataStore);
        this.inputManager = new InputManager(this.widgetManager, this.bindingManager);
        setBounds(bounds);
        
        this.screenFilenameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File arg0, String arg1) {
                return arg1.endsWith(".screen");
            }
        };
        loadScreens();
        // Temporary first console selection
        this.bindingManager.setCurrentBindings("First Console");
        this.widgetManager.setCurrentWidgetLayout("First Console");
    }
    
    private void loadScreens() {
    	File screensFolder = new File("screens/");
        File[] screenFiles = screensFolder.listFiles(screenFilenameFilter);
        for(int i = 0; i < screenFiles.length; i++) {
            FileReader screenFileReader; 
            try {
                screenFileReader = new FileReader(screenFiles[i]);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                continue;
            }
            ScreenConfig.Builder config = ScreenConfig.newBuilder();
            try {
                TextFormat.merge(screenFileReader, config);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            this.widgetManager.registerWidgetLayout(config.getScreenName(), config.getWidgetLayout());
            this.bindingManager.registerBindings(config.getScreenName(), config.getBindings());
        }
    }

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SpecialisedDisplays nextDisplay() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBounds(Rectangle bounds) {
		// TODO Auto-generated method stub
		
	}
}
