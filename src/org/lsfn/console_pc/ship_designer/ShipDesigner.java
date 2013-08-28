package org.lsfn.console_pc.ship_designer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.lsfn.console_pc.data_management.elements.ControlledData;
import org.lsfn.console_pc.data_management.elements.DataSource;
import org.lsfn.console_pc.ship_designer.ShipDesignFile.ShipDesign;

public class ShipDesigner implements DataSource, ControlledData {

    private Component parent;
    private JFileChooser fileDialog;
    private ShipDesignData shipDesign;
    
    private FileFilter imageFileFilter;
    private FileFilter shipDesignFileFilter;
    
    private int width, height;
    private Rectangle menu, menuImportImage, menuSaveShipDesign, design;
    
    public ShipDesigner(Component parent) {
        this.parent = parent;
        this.fileDialog = new JFileChooser(".");
        this.shipDesign = null;
        
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
        
        this.width = 100;
        this.height = 100;
    }
    
    private void reconsiderBounds(Rectangle bounds) {
        if(bounds.width != this.width || bounds.height != this.height) {
            this.width = bounds.width;
            this.height = bounds.height;
            this.menu = new Rectangle(0, 0, (int)(bounds.getWidth()/4), bounds.height);
            this.menuImportImage = new Rectangle(10, 10, this.menu.width - 20, (this.menu.height / 2) - 20);
            this.menuSaveShipDesign = new Rectangle(10, (this.menu.height / 2) + 10, this.menu.width - 20, (this.menu.height / 2) - 20);
            this.design = new Rectangle((int)(bounds.getWidth()/4), 0, (int)(bounds.getWidth() - (bounds.getWidth()/4)), bounds.height);
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
        case KeyEvent.VK_Q:
            this.shipDesign.decreaseGridSize();
            break;
        case KeyEvent.VK_E:
            this.shipDesign.increaseGridSize();
            break;
        case KeyEvent.VK_W:
            this.shipDesign.moveGridUp();
            break;
        case KeyEvent.VK_A:
            this.shipDesign.moveGridLeft();
            break;
        case KeyEvent.VK_S:
            this.shipDesign.moveGridDown();
            break;
        case KeyEvent.VK_D:
            this.shipDesign.moveGridRight();
            break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(this.menuImportImage.contains(e.getPoint())) {
            this.fileDialog.setFileFilter(this.imageFileFilter);
            int returnVal = this.fileDialog.showOpenDialog(this.parent);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File shipImageFile = this.fileDialog.getSelectedFile();
                try {
                    BufferedImage shipImage = ImageIO.read(shipImageFile);
                    this.shipDesign = new ShipDesignData(shipImageFile.getName(), shipImage);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }                
            }
        } else if(this.menuSaveShipDesign.contains(e.getPoint())) {
            this.fileDialog.setFileFilter(this.shipDesignFileFilter);
            int returnVal = this.fileDialog.showSaveDialog(this.parent);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File shipDesignFile = this.fileDialog.getSelectedFile();
                try {
                    FileWriter fw = new FileWriter(shipDesignFile);
                    BufferedWriter writer = new BufferedWriter(fw);
                    ShipDesign design = this.shipDesign.getSerialisedDesign();
                    writer.write(new String(design.toByteArray()));
                    writer.close();
                } catch(IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public Object getData() {
        return null;
    }
    
    public void drawDesigner(Graphics2D g, Rectangle bounds) {
        reconsiderBounds(bounds);        
        g.setColor(Color.BLACK);
        g.fill(bounds);
        g.setColor(Color.BLUE);
        g.fill(menu);
        g.setColor(new Color(0, 0, 128));
        g.fill(menuImportImage);
        g.fill(menuSaveShipDesign);
        if(this.shipDesign != null) {
            // Draw the ship by first determining a transform for where it should be drawn
            BufferedImage shipImage = this.shipDesign.getShipImage();
            double scaleX = (double)this.design.width / shipImage.getWidth();
            double scaleY = (double)this.design.height / shipImage.getHeight();
            double scaleToUse = scaleX;
            if(scaleY < scaleX) {
                scaleToUse = scaleY;
            }
            double xPos = this.design.getCenterX() - ((scaleToUse * shipImage.getWidth()) / 2);
            double yPos = this.design.getCenterY() - ((scaleToUse * shipImage.getHeight()) / 2);
            AffineTransform shipImageTransform = AffineTransform.getTranslateInstance(xPos, yPos);
            shipImageTransform.scale(scaleToUse, scaleToUse);
            g.drawImage(shipImage, shipImageTransform, null);
            
            // Draw the ship boundary
            g.setColor(Color.WHITE);
            Polygon polygon = this.shipDesign.getBoundaryPolygon();
            PathIterator transformedPath = polygon.getPathIterator(shipImageTransform);
            Polygon transformedPolygon = new Polygon();
            double points[] = new double[6];
            while(!transformedPath.isDone()) {
                transformedPath.currentSegment(points);
                transformedPolygon.addPoint((int)points[0], (int)points[1]);
                transformedPath.next();
            }
            g.drawPolygon(transformedPolygon);
            
            // Draw the grid squares
            g.setColor(Color.RED);
            for(Rectangle rect : this.shipDesign.getGridSquares()) {
                PathIterator transformedRectPath = rect.getPathIterator(shipImageTransform);
                transformedPolygon = new Polygon();
                while(!transformedRectPath.isDone()) {
                    transformedRectPath.currentSegment(points);
                    transformedPolygon.addPoint((int)points[0], (int)points[1]);
                    transformedRectPath.next();
                }
                Rectangle transformedRect = transformedPolygon.getBounds();
                g.draw(transformedRect);
                // TODO better logging
            }
        }
    }

}
