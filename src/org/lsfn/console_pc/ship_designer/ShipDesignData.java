package org.lsfn.console_pc.ship_designer;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.HashSet;
import java.util.Set;

import org.lsfn.console_pc.ship_designer.ShipDesignFile.ShipDesign;

public class ShipDesignData {
    
    private static final int lowestPixelsPerMetre = 10;
    
    private String shipImageFileName;
    private BufferedImage shipImage;
    private Polygon boundaryPolygon;
    private int gridOffsetX, gridOffsetY, gridSize;
    private Set<Rectangle> gridSquares;
    
    public ShipDesignData(String shipImageFileName, BufferedImage shipImage) {
        this.shipImageFileName = shipImageFileName;
        this.shipImage = shipImage;
        computeHull();
        this.gridOffsetX = 0;
        this.gridOffsetY = 0;
        this.gridSize = Math.min(shipImage.getWidth(), shipImage.getHeight()) / 3;
        computeGrid();
    }
    
    public BufferedImage getShipImage() {
        return this.shipImage;
    }
    
    private void computeHull() {
        int width = this.shipImage.getWidth(), height = this.shipImage.getHeight();
        int transparencyMatrix[][] = new int[width][height];
        WritableRaster alphaRaster = this.shipImage.getAlphaRaster();
        int temp[] = new int[1];
        for(int x = 0; x < this.shipImage.getWidth(); x++) {
            for(int y = 0; y < this.shipImage.getHeight(); y++) {
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
        this.boundaryPolygon = polygon;
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

    public Polygon getBoundaryPolygon() {
        return this.boundaryPolygon;
    }
    
    private void computeGrid() {
        Set<Rectangle> rectanglesInsideHull = new HashSet<Rectangle>();
        int horizRects = this.shipImage.getWidth() / this.gridSize;
        int vertRects = this.shipImage.getHeight() / this.gridSize;
        for(int x = 0; x < horizRects; x++) {
            for(int y = 0; y < vertRects; y++) {
                Rectangle currentGridSquare = new Rectangle(this.gridOffsetX + (x * this.gridSize), gridOffsetY + (y * this.gridSize), this.gridSize, this.gridSize);
                if(this.boundaryPolygon.contains(currentGridSquare)) {
                    rectanglesInsideHull.add(currentGridSquare);
                }
            }
        }
        this.gridSquares = rectanglesInsideHull;
    }
    
    public void increaseGridSize() {
        if(this.gridSize < Math.min(this.shipImage.getWidth(), this.shipImage.getHeight()) / 3) {
            this.gridSize++;
        }
        computeGrid();
    }
    
    public void decreaseGridSize() {
        if(this.gridSize > lowestPixelsPerMetre) {
            this.gridSize--;
        }
        computeGrid();
    }
    
    public void moveGridRight() {
        this.gridOffsetX = (this.gridOffsetX + 1) % this.gridSize;
        computeGrid();
    }
    
    public void moveGridLeft() {
        this.gridOffsetX = (this.gridOffsetX + this.gridSize - 1) % this.gridSize;
        computeGrid();
    }
    
    public void moveGridDown() {
        this.gridOffsetY = (this.gridOffsetY + 1) % this.gridSize;
        computeGrid();
    }
    
    public void moveGridUp() {
        this.gridOffsetY = (this.gridOffsetY + this.gridSize - 1) % this.gridSize;
        computeGrid();
    }
    
    public Set<Rectangle> getGridSquares() {
        return this.gridSquares;
    }
    
    public ShipDesign getSerialisedDesign() {
        ShipDesign.Builder shipDesign = ShipDesign.newBuilder();
        shipDesign.setShipImageFileName(shipImageFileName);
        shipDesign.setGridSize(gridSize);
        shipDesign.setGridOffset(ShipDesign.Point.newBuilder().setX(gridOffsetX).setY(gridOffsetY));
        ShipDesign.Polygon.Builder shipDesignPolygon = ShipDesign.Polygon.newBuilder();
        int[] xPoints = this.boundaryPolygon.xpoints;
        int[] yPoints = this.boundaryPolygon.ypoints;
        for(int i = 0; i < this.boundaryPolygon.npoints; i++) {
            shipDesignPolygon.addPoints(ShipDesign.Point.newBuilder().setX(xPoints[i]).setY(yPoints[i]));
        }
        shipDesign.setShipBoundary(shipDesignPolygon);
        return shipDesign.build();
    }
}
