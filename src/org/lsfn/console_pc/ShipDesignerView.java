package org.lsfn.console_pc;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

public class ShipDesignerView implements View {

    private DataManager dataManager;
    private ScreenManager viewManager;
    private JFileChooser fileDialog;
    private BufferedImage shipImage;
    
    private Rectangle chooseImageButton, shipDisplayBox;
    
    private enum ShipDesignerComponents {
        NONE, 
        CHOOSE_IMAGE_BUTTON,
        SHIP_DISPLAY_BOX
    }
    private ShipDesignerComponents lastComponentClicked;
    
    public ShipDesignerView(ScreenManager viewManager, DataManager dataManager) {
        this.viewManager = viewManager;
        this.dataManager = dataManager;
        this.fileDialog = new JFileChooser();
        
        this.chooseImageButton = new Rectangle(10, 10, 100, 30);
        this.shipDisplayBox = new Rectangle(120, 10, 200, 200);
        
        this.lastComponentClicked = ShipDesignerComponents.NONE;
    }
    
    private void selectFile() {
        int returnVal = this.fileDialog.showOpenDialog(viewManager);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            // Load the file
            File shipImageFile = this.fileDialog.getSelectedFile();
            shipImage = null;
            try {
                shipImage = ImageIO.read(shipImageFile);
            } catch (IOException e) {
                // Error reading the file.
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void drawView(Graphics2D g, Rectangle bounds) {
        g.setColor(Color.BLUE);
        g.fill(chooseImageButton);
        g.setColor(Color.WHITE);
        g.drawString("Choose image", this.chooseImageButton.x + 10, this.chooseImageButton.y + 20);
        g.setColor(Color.DARK_GRAY);
        g.fill(shipDisplayBox);
        if(this.shipImage != null) {
            double scaleX = (double)this.shipDisplayBox.width / (double)this.shipImage.getWidth();
            double scaleY = (double)this.shipDisplayBox.height / (double)this.shipImage.getHeight();
            AffineTransform at = AffineTransform.getScaleInstance(scaleX, scaleY);
            at.translate(this.shipDisplayBox.x, this.shipDisplayBox.y);
            g.drawImage(this.shipImage, at, null);
        }
    }
    
    @Override
    public void keyPressed(KeyEvent arg0) {

    }

    @Override
    public void keyReleased(KeyEvent arg0) {

    }

    @Override
    public void keyTyped(KeyEvent arg0) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(this.chooseImageButton.contains(e.getPoint())) {
            this.lastComponentClicked = ShipDesignerComponents.CHOOSE_IMAGE_BUTTON;
            selectFile();
        } else if(this.shipDisplayBox.contains(e.getPoint())) {
            this.lastComponentClicked = ShipDesignerComponents.SHIP_DISPLAY_BOX;
        } else {
            this.lastComponentClicked = ShipDesignerComponents.NONE;
        }
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {

    }

    @Override
    public void mouseExited(MouseEvent arg0) {

    }

    @Override
    public void mousePressed(MouseEvent arg0) {

    }

    @Override
    public void mouseReleased(MouseEvent arg0) {

    }

}
