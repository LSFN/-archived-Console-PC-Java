package org.lsfn.console_pc.ship_designer;

import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class ShipDesignData {
    
    private BufferedImage shipImage;
    private Polygon boundaryPolygon;
    
    public ShipDesignData(BufferedImage shipImage) {
        this.shipImage = shipImage;
        this.boundaryPolygon = computeHull(shipImage);
    }
    
    private Polygon computeHull(BufferedImage image) {
        int width = image.getWidth(), height = image.getHeight();
        int transparencyMatrix[][] = new int[width][height];
        WritableRaster alphaRaster = image.getAlphaRaster();
        int temp[] = new int[1];
        for(int x = 0; x < image.getWidth(); x++) {
            for(int y = 0; y < image.getHeight(); y++) {
                if(alphaRaster.getPixel(x, y, temp)[0] == 255) {
                    // opaque
                    transparencyMatrix[x][y] = 1;
                } else {
                    transparencyMatrix[x][y] = 0;
                }
            }
        }
        
        // Find a pixel on the boundary
        int x = -1, y = -1;
        boolean found = false;
        int a = 0;
        while(a < width && !found) {
            int b = 0;
            while(b < height && !found) {
                if(transparencyMatrix[a][b] == 1) {
                    // The now selected pixel has transparent or non-existent pixels to the left, up-left and up from it
                    x = a;
                    y = b;
                    found = true;
                }
                b++;
            }
            a++;
        }
        
        Polygon polygon = new Polygon();
        polygon.addPoint(x, y);
        int startX = x, startY = y;
        int dir = 0; // dirs: 0 = right, 1 = up, 2 = left, 3 = down
        x = dirMoveX(x, dir);
        y = dirMoveY(y, dir);
        while(x != startX || y != startY) {
            /* Pixel indicies:
             * 1|0
             * -*-
             * 2|3
             */
            
            // Determine the surrounding pixels
            int surroundingFourPixels[] = new int[4];
            if(x == 0) {
                surroundingFourPixels[1] = 0;
                surroundingFourPixels[2] = 0;
                if(y == 0) {
                    surroundingFourPixels[0] = 0;
                    surroundingFourPixels[3] = transparencyMatrix[x][y];
                } else if(y == height) {
                    surroundingFourPixels[0] = transparencyMatrix[x][y-1];
                    surroundingFourPixels[3] = 0;
                }
            } else if(x == width) {
                surroundingFourPixels[0] = 0;
                surroundingFourPixels[3] = 0;
                if(y == 0) {
                    surroundingFourPixels[1] = 0;
                    surroundingFourPixels[2] = transparencyMatrix[x-1][y];
                } else if(y == height) {
                    surroundingFourPixels[1] = transparencyMatrix[x-1][y-1];
                    surroundingFourPixels[2] = 0;
                }
            } else {
                surroundingFourPixels[0] = transparencyMatrix[x][y-1];
                surroundingFourPixels[1] = transparencyMatrix[x-1][y-1];
                surroundingFourPixels[2] = transparencyMatrix[x-1][y];
                surroundingFourPixels[3] = transparencyMatrix[x][y];
            }
            
            // Determine the direction we should travel in next
            if(surroundingFourPixels[(dir + 3) % 4] == 1) {
                if(surroundingFourPixels[dir] == 1) {
                    // turn left
                    dir = (dir + 1) % 4;
                    polygon.addPoint(x, y);
                }
            } else {
                // turn right
                dir = (dir + 3) % 4;
                polygon.addPoint(x, y);
            }
            
            // Travel in the direction we should go in
            x = dirMoveX(x, dir);
            y = dirMoveY(y, dir);
        }
        return polygon;
    }
    
    private int dirMoveX(int x, int dir) {
        if(dir == 0) return x + 1;
        if(dir == 2) return x - 1;
        return x;
    }
    
    private int dirMoveY(int y, int dir) {
        if(dir == 3) return y + 1;
        if(dir == 1) return y - 1;
        return y;
    }

    public BufferedImage getShipImage() {
        return this.shipImage;
    }
    
    public Polygon getBoundaryPolygon() {
        return this.boundaryPolygon;
    }
}
