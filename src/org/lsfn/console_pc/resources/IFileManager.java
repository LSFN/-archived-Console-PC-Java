package org.lsfn.console_pc.resources;

import java.awt.image.BufferedImage;

import org.lsfn.console_pc.ship_designer.ShipDesignFile.ShipDesign;

public interface IFileManager {
    
    public void loadScreens();
    public ShipDesign openShipDesignFile();
    public void saveShipDesignFile(ShipDesign shipDesign);
    public BufferedImage openShipImageFile();
}
