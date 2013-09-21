package org.lsfn.console_pc.resources;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.lsfn.console_pc.custom_consoles.screen.ScreenFile;
import org.lsfn.console_pc.custom_consoles.screen.ScreenFile.ScreenConfig;
import org.lsfn.console_pc.ship_designer.ShipDesignFile.ShipDesign;

import com.google.protobuf.TextFormat;

public class FileManager implements IFileManager {

    private JFileChooser fileChooser;
    private Component parent;
    private FilenameFilter screenFilenameFilter;
    private FileFilter imageFileFilter;
    private FileFilter shipDesignFileFilter;
    
    private Map<String, ScreenConfig> screenConfigs;
    
    public FileManager(Component parent) {
        this.parent = parent;
        this.fileChooser = new JFileChooser(".");
        
        this.screenFilenameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File arg0, String arg1) {
                return arg1.endsWith(".screen");
            }
        };
        
        this.imageFileFilter = new FileFilter() {
            @Override
            public String getDescription() {
                return "Image files that permit transparency";
            }
            
            @Override
            public boolean accept(File f) {
                if(f.isDirectory()) {
                    return true;
                } else if(f.getName().endsWith(".png") ||
                        f.getName().endsWith(".jpg") ||
                        f.getName().endsWith(".jpeg")) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        
        this.shipDesignFileFilter = new FileFilter() {
            @Override
            public String getDescription() {
                return "LSFN Ship designs";
            }
            
            @Override
            public boolean accept(File f) {
                if(f.isDirectory()) {
                    return true;
                } else if(f.getName().endsWith(".ship")) {
                    return true;
                } else {
                    return false;
                }
            }
        };
    }
    
    @Override
    public void loadScreens() {
        File screensFolder = new File("screens/");
        File[] screenFiles = screensFolder.listFiles(screenFilenameFilter);
        this.screenConfigs = new HashMap<String, ScreenFile.ScreenConfig>();
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
            this.screenConfigs.put(config.getScreenName(), config.build());
        }
    }

    @Override
    public ShipDesign openShipDesignFile() {
        this.fileChooser.setFileFilter(this.shipDesignFileFilter);
        int retVal = this.fileChooser.showOpenDialog(this.parent);
        if(retVal == JFileChooser.APPROVE_OPTION) {
            File shipDesignFile = this.fileChooser.getSelectedFile();
            try {
                FileInputStream fileInputStream = new FileInputStream(shipDesignFile);
                return ShipDesign.parseFrom(fileInputStream);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }
    
    @Override
    public void saveShipDesignFile(ShipDesign shipDesign) {
        this.fileChooser.setFileFilter(this.shipDesignFileFilter);
        int retVal = this.fileChooser.showOpenDialog(this.parent);
        if(retVal == JFileChooser.APPROVE_OPTION) {
            File shipDesignFile = this.fileChooser.getSelectedFile();
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(shipDesignFile);
                shipDesign.writeTo(fileOutputStream);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public BufferedImage openShipImageFile() {
        this.fileChooser.setFileFilter(this.imageFileFilter);
        int retVal = this.fileChooser.showOpenDialog(this.parent);
        if(retVal == JFileChooser.APPROVE_OPTION) {
            File shipImageFile = this.fileChooser.getSelectedFile();
            try {
                BufferedImage shipImage = ImageIO.read(shipImageFile);
                return shipImage;
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }
}
