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
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.lsfn.console_pc.data_management.elements.ControlledData;
import org.lsfn.console_pc.data_management.elements.DataSource;

public class ShipDesigner implements DataSource, ControlledData {

    private Component parent;
    private JFileChooser fileDialog;
    private ShipDesignData shipDesign;
    
    private int width, height;
    private Rectangle menu, design;
    
    public ShipDesigner(Component parent) {
        this.parent = parent;
        this.fileDialog = new JFileChooser(".");
        this.fileDialog.addChoosableFileFilter(new FileFilter() {
            
            @Override
            public String getDescription() {
                return "Image files that permit transparency.";
            }
            
            @Override
            public boolean accept(File f) {
                if(f.getName().endsWith(".png") ||
                        f.getName().endsWith(".jpg") ||
                        f.getName().endsWith(".jpeg")) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        this.shipDesign = null;
        
        this.width = 100;
        this.height = 100;
    }
    
    private void reconsiderBounds(Rectangle bounds) {
        if(bounds.width != this.width || bounds.height != this.height) {
            this.width = bounds.width;
            this.height = bounds.height;
            this.menu = new Rectangle(0, 0, (int)(bounds.getWidth()/4), bounds.height);
            this.design = new Rectangle((int)(bounds.getWidth()/4), 0, (int)(bounds.getWidth() - (bounds.getWidth()/4)), bounds.height);
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(this.menu.contains(e.getPoint())) {
            int returnVal = this.fileDialog.showOpenDialog(this.parent);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File shipImageFile = this.fileDialog.getSelectedFile();
                try {
                    BufferedImage shipImage = ImageIO.read(shipImageFile);
                    this.shipDesign = new ShipDesignData(shipImage);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }                
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public Object getData() {
        // TODO Auto-generated method stub
        return null;
    }
    
    public void drawDesigner(Graphics2D g, Rectangle bounds) {
        reconsiderBounds(bounds);        
        g.setColor(Color.BLACK);
        g.fill(bounds);
        g.setColor(Color.BLUE);
        g.fill(menu);
        if(this.shipDesign != null) {
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
            //g.drawImage(this.shipDesign.getTransparencyImage(), shipImageTransform, null);
            //g.drawImage(this.shipDesign.getBoundaryImage(), shipImageTransform, null);
            
            
            g.setColor(Color.WHITE);
            for(Polygon polygon : this.shipDesign.getBoundaryPolygons()) {
                PathIterator transformedPath = polygon.getPathIterator(shipImageTransform);
                Polygon transformedPolygon = new Polygon();
                double points[] = new double[6];
                while(!transformedPath.isDone()) {
                    transformedPath.currentSegment(points);
                    transformedPolygon.addPoint((int)points[0], (int)points[1]);
                    transformedPath.next();
                }
                g.drawPolygon(transformedPolygon);
            }
            
        }
    }

}
