package org.lsfn.console_pc;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.lsfn.console_pc.SpaceObject.ObjectType;

public class PilotingView implements View {

    // TODO zoom
    private static int scale = 10;
    
    private DataManager dataManager;
    
    public PilotingView(DataManager dataManager) {
        this.dataManager = dataManager;
    }
    
    private void paintCalibration(Graphics2D g, Rectangle bounds) {
        int squareSize = 10;
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, squareSize, squareSize);
        g.fillRect(bounds.width - squareSize - 1, 0, squareSize, squareSize);
        g.fillRect(0, bounds.height - squareSize - 1, squareSize, squareSize);
        g.fillRect(bounds.width - squareSize - 1, bounds.height - squareSize - 1, squareSize, squareSize);
        g.setColor(Color.RED);
        g.drawRect(0, 0, squareSize, squareSize);
        g.drawRect(bounds.width - squareSize - 1, 0, squareSize, squareSize);
        g.drawRect(0, bounds.height - squareSize - 1, squareSize, squareSize);
        g.drawRect(bounds.width - squareSize - 1, bounds.height - squareSize - 1, squareSize, squareSize);
    }
    
    private void paintBackground(Graphics2D g, Rectangle bounds) {
        int width = bounds.width;
        int height = bounds.height;
        g.setColor(new Color(0, 0, 128));
        if(width > height) {
            g.fillOval(width/2 - height/2, 0, height, height);
        } else {
            g.fillOval(0, height/2 - width/2, width, width);
        }        
    }
    
    private void paintObjects(Graphics2D g, Rectangle bounds) {
        for(SpaceObject so : this.dataManager.getVisualSensorsSpaceObjects()) {
            if(so.getObjectType() == ObjectType.SHIP) {
                int[] xs = new int[3];
                int[] ys = new int[3];
                xs[0] = (int)(convertX((-Math.sin(so.getTheta()) * 2) + so.getX(), bounds));
                ys[0] = (int)(convertY((Math.cos(so.getTheta()) * 2) + so.getY(), bounds));
                xs[1] = (int)(convertX(Math.cos(so.getTheta()) + so.getX(), bounds));
                ys[1] = (int)(convertY(Math.sin(so.getTheta()) + so.getY(), bounds));
                xs[2] = (int)(convertX(-Math.cos(so.getTheta()) + so.getX(), bounds));
                ys[2] = (int)(convertY(-Math.sin(so.getTheta()) + so.getY(), bounds));
                g.setColor(Color.GREEN);
                g.fillPolygon(xs, ys, 3);
            } else if(so.getObjectType() == ObjectType.ASTEROID) {
                g.setColor(Color.YELLOW);
                g.fillOval((int)convertX(so.getX(), bounds) - scale, (int)convertY(so.getY(), bounds) - scale, scale*2, scale*2);
            }
        }
    }
    
    private double convertX(double x, Rectangle bounds) {
        return (x * scale) + (bounds.width/2);
    }
    
    private double convertY(double y, Rectangle bounds) {
        return -(y * scale) + (bounds.height/2);
    }
    
    private void paintOwnShip(Graphics2D g2d, Rectangle bounds) {
        int centreX = bounds.width / 2;
        int centreY = bounds.height / 2;
        int[] xs = new int[3];
        int[] ys = new int[3];
        xs[0] = centreX;
        ys[0] = -(2 * scale) + centreY;
        xs[1] = -scale + centreX;
        ys[1] = centreY;
        xs[2] = scale + centreX;
        ys[2] = centreY;
        g2d.setColor(Color.GREEN);
        g2d.fillPolygon(xs, ys, 3);
    }
    
    private void clearPanel(Graphics2D g, Rectangle bounds) {
        g.setColor(Color.BLACK);
        g.fill(bounds);
    }
    
    @Override
    public void drawView(Graphics2D g, Rectangle bounds) {
        clearPanel(g, bounds);
        paintBackground(g, bounds);
        paintObjects(g, bounds);
        paintOwnShip(g, bounds);
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_Q) {
            this.dataManager.setTurnAnti(true);
        } else if(e.getKeyCode() == KeyEvent.VK_E) {
            this.dataManager.setTurnClock(true);
        } else if(e.getKeyCode() == KeyEvent.VK_A) {
            this.dataManager.setThrustLeft(true);
        } else if(e.getKeyCode() == KeyEvent.VK_D) {
            this.dataManager.setThrustRight(true);
        } else if(e.getKeyCode() == KeyEvent.VK_W) {
            this.dataManager.setThrustForward(true);
        } else if(e.getKeyCode() == KeyEvent.VK_S) {
            this.dataManager.setThrustBackward(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_Q) {
            this.dataManager.setTurnAnti(false);
        } else if(e.getKeyCode() == KeyEvent.VK_E) {
            this.dataManager.setTurnClock(false);
        } else if(e.getKeyCode() == KeyEvent.VK_A) {
            this.dataManager.setThrustLeft(false);
        } else if(e.getKeyCode() == KeyEvent.VK_D) {
            this.dataManager.setThrustRight(false);
        } else if(e.getKeyCode() == KeyEvent.VK_W) {
            this.dataManager.setThrustForward(false);
        } else if(e.getKeyCode() == KeyEvent.VK_S) {
            this.dataManager.setThrustBackward(false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

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
    

}
