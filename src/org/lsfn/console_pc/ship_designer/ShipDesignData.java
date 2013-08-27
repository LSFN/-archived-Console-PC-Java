package org.lsfn.console_pc.ship_designer;

import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShipDesignData {
    
    private BufferedImage shipImage;
    private BufferedImage transparencyImage; // Purely for debug purposes
    private BufferedImage boundaryImage; // Purely for debug purposes
    private Set<Polygon> boundaryPolygons;
    private Polygon boundaryPolygon;
    
    public ShipDesignData(BufferedImage shipImage) {
        this.shipImage = shipImage;
        //computeHull();
        //this.boundaryPolygons = computeBoundaries();
        this.boundaryPolygon = computeOutline(computeTransparencyMatrix(shipImage), shipImage.getWidth(), shipImage.getHeight());
    }
    
    private int[][] computeTransparencyMatrix(BufferedImage image) {
        int transparencyMatrix[][] = new int[image.getWidth()][image.getHeight()];
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
        return transparencyMatrix;
    }
    
    private Polygon computeOutline(int[][] objectMatrix, int width, int height) {
        // Find a pixel on the boundary
        int x = -1, y = -1;
        boolean found = false;
        int a = 0;
        while(a < width && !found) {
            int b = 0;
            while(b < height && !found) {
                if(objectMatrix[a][b] == 1) {
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
                    surroundingFourPixels[3] = objectMatrix[x][y];
                } else if(y == height) {
                    surroundingFourPixels[0] = objectMatrix[x][y-1];
                    surroundingFourPixels[3] = 0;
                }
            } else if(x == width) {
                surroundingFourPixels[0] = 0;
                surroundingFourPixels[3] = 0;
                if(y == 0) {
                    surroundingFourPixels[1] = 0;
                    surroundingFourPixels[2] = objectMatrix[x-1][y];
                } else if(y == height) {
                    surroundingFourPixels[1] = objectMatrix[x-1][y-1];
                    surroundingFourPixels[2] = 0;
                }
            } else {
                surroundingFourPixels[0] = objectMatrix[x][y-1];
                surroundingFourPixels[1] = objectMatrix[x-1][y-1];
                surroundingFourPixels[2] = objectMatrix[x-1][y];
                surroundingFourPixels[3] = objectMatrix[x][y];
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
    
    private Set<Polygon> computeBoundaries() {
        int width = shipImage.getWidth();
        int height = shipImage.getHeight();
        
        // Compute the matrix of opaque pixels versus those that are partially / fully transparent
        int transparencyMatrix[][] = new int[width][height];
        transparencyImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        WritableRaster alphaRaster = shipImage.getAlphaRaster();
        int temp[] = new int[1];
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                if(alphaRaster.getPixel(x, y, temp)[0] == 255) {
                    // opaque
                    transparencyMatrix[x][y] = 1;
                    transparencyImage.setRGB(x, y, 0xFFFFFF);
                } else {
                    transparencyMatrix[x][y] = 0;
                    transparencyImage.setRGB(x, y, 0xDDDDDD);
                }
            }
        }
        
        // Compute the matrix of transparent / opaque boundary pixels
        int boundaryMatrix[][] = new int[width][height];
        boundaryImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for(int x = 1; x < width-1; x++) {
            for(int y = 1; y < height-1; y++) {
                if(transparencyMatrix[x][y] == 1 && (transparencyMatrix[x][y+1] == 0 || transparencyMatrix[x][y-1] == 0 ||
                        transparencyMatrix[x+1][y] == 0 || transparencyMatrix[x-1][y] == 0)) {
                    boundaryMatrix[x][y] = 1;
                    boundaryImage.setRGB(x, y, 0x000000);
                } else {
                    boundaryMatrix[x][y] = 0;
                    boundaryImage.setRGB(x, y, 0xFFFFFF);
                }
            }
        }
        
        // Find a boundary pixel and extract a polygon from it
        Set<Polygon> polygons = new HashSet<Polygon>();
        for(int x = 1; x < width-1; x++) {
            for(int y = 1; y < height-1; y++) {
                if(boundaryMatrix[x][y] == 1) {
                    // Extract a polygon starting from this pixel
                    Polygon poly = new Polygon();
                    poly.addPoint(x, y);
                    boundaryMatrix[x][y] = 0;
                    int startX = x, startY = y;
                    int dir = searchClockwise(boundaryMatrix, x, y, 2);
                    boolean loopComplete = false;
                    while(!loopComplete) {
                        while(valueInDirection(boundaryMatrix, x, y, dir) == 1) {
                            int[] coords = coordinatesInDirection(x, y, dir);
                            x = coords[0];
                            y = coords[1];
                        }
                        poly.addPoint(x, y);
                        boundaryMatrix[x][y] = 0;
                        int revDir = getReverseDir(dir);
                        dir = searchClockwise(boundaryMatrix, x, y, revDir);
                        if(revDir == dir) {
                            loopComplete = true;
                        }
                    }
                    polygons.add(poly);
                }
            }
        }
        
        return polygons;
    }
    
    private int getReverseDir(int dir) {
        return (dir + 4) % 8;
    }
    
    /**
     * Find the next direction clockwise from the provided dir that cont
     * @param matrix
     * @param x
     * @param y
     * @param dir
     * @return
     */
    private int searchClockwise(int[][] matrix, int x, int y, int dir) {
        int[] vals = surroundingValues(matrix, x, y);
        int currentDir = (dir + 7) % 8;
        while(currentDir != dir) {
            if(vals[currentDir] == 1) {
                return currentDir;
            }
            currentDir = (currentDir + 7) % 8;
        }
        return currentDir;
    }
    
    /**
     * Gets the values of the matrix surrounding a given cell;
     * Directions are as follows:
     * 3 2 1
     * 4 * 0
     * 5 6 7
     * 
     * @param matrix
     * @param x
     * @param y
     * @return Values surrounding the cell in a direction-indexed integer array
     */
    private int[] surroundingValues(int[][] matrix, int x, int y) {
        /*
         * 3 2 1
         * 4 * 0
         * 5 6 7
         */
        int dirs[] = new int[8];
        dirs[0] = matrix[x+1][y];
        dirs[1] = matrix[x+1][y-1];
        dirs[2] = matrix[x][y-1];
        dirs[3] = matrix[x-1][y-1];
        dirs[4] = matrix[x-1][y];
        dirs[5] = matrix[x-1][y+1];
        dirs[6] = matrix[x][y+1];
        dirs[7] = matrix[x+1][y+1];
        return dirs;
    }
    
    private int[] coordinatesInDirection(int x, int y, int dir) {
        int coords[] = {x, y};
        if(dir == 7 || dir == 0 || dir == 1) {
            coords[0]++;
        } else if(dir == 3 || dir == 4 || dir == 5) {
            coords[0]--;
        }
        if(dir == 1 || dir == 2 || dir == 3) {
            coords[1]--;
        } else if(dir == 5 || dir == 6 || dir == 7) {
            coords[1]++;
        }
        return coords;
    }
    
    private int valueInDirection(int[][] matrix, int x, int y, int dir) {
        if(dir == 7 || dir == 0 || dir == 1) {
            x++;
        } else if(dir == 3 || dir == 4 || dir == 5) {
            x--;
        }
        if(dir == 1 || dir == 2 || dir == 3) {
            y--;
        } else if(dir == 5 || dir == 6 || dir == 7) {
            y++;
        }
        return matrix[x][y];
    }
    
    private void computeHull() {
        int width = shipImage.getWidth();
        int height = shipImage.getHeight();
        
        // Compute the matrix of opaque pixels versus those that are partially / fully transparent
        int transparencyMatrix[][] = new int[width][height];
        transparencyImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        WritableRaster alphaRaster = shipImage.getAlphaRaster();
        int temp[] = new int[1];
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                if(alphaRaster.getPixel(x, y, temp)[0] == 255) {
                    transparencyMatrix[x][y] = 1;
                    transparencyImage.setRGB(x, y, 0xFFFFFF);
                } else {
                    transparencyMatrix[x][y] = 0;
                    transparencyImage.setRGB(x, y, 0xDDDDDD);
                }
            }
        }
        
        // Compute the matrix of transparent / opaque boundary pixels
        int boundaryMatrix[][] = new int[width][height];
        boundaryImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for(int x = 1; x < width-1; x++) {
            for(int y = 1; y < height-1; y++) {
                if(transparencyMatrix[x][y] == 1 && (transparencyMatrix[x][y+1] == 0 || transparencyMatrix[x][y-1] == 0 ||
                        transparencyMatrix[x+1][y] == 0 || transparencyMatrix[x-1][y] == 0)) {
                    boundaryMatrix[x][y] = 1;
                    boundaryImage.setRGB(x, y, 0x000000);
                } else {
                    boundaryMatrix[x][y] = 0;
                    boundaryImage.setRGB(x, y, 0xFFFFFF);
                }
            }
        }
        
        // Compute complete lines from boundary
        List<Line> completeLines = new ArrayList<Line>();
        //  Right
        for(int y = 1; y < height-1; y++) {
            Line currentLine = null;
            // We go to width not width-1 in order to store any line going to the edge
            for(int x = 1; x < width; x++) {
                if(boundaryMatrix[x][y] == 1) {
                    if(currentLine == null) {
                        currentLine = new Line(x, y, x, y);
                    } else {
                        currentLine.x2++;
                    }
                } else {
                    if(currentLine != null) {
                        if(currentLine.x1 != currentLine.x2) {
                            // We don't add length 1 lines
                            completeLines.add(currentLine);
                        }
                        currentLine = null;
                    }
                }
            }
        }
        //  Down-right
        //   This is more complex because it's diagonal
        //   First we tackle the bottom-left 'triangle'
        //   (not necessarily a triangle)
        for(int a = 1; a < height-2; a++) {
            Line currentLine = null;
            for(int x = 0, y = a; x < width && y < height; x++, y++) {
                if(boundaryMatrix[x][y] == 1) {
                    if(currentLine == null) {
                        currentLine = new Line(x, y, x, y);
                    } else {
                        currentLine.x2++;
                        currentLine.y2++;
                    }
                } else {
                    if(currentLine != null) {
                        if(currentLine.x1 != currentLine.x2) {
                            // We don't add length 1 lines
                            completeLines.add(currentLine);
                        }
                        currentLine = null;
                    }
                }
            }
        }
        //   then the top-right 'triangle'
        for(int a = 0; a < width-2; a++) {
            Line currentLine = null;
            for(int x = a, y = 0; x < width && y < height; x++, y++) {
                if(boundaryMatrix[x][y] == 1) {
                    if(currentLine == null) {
                        currentLine = new Line(x, y, x, y);
                    } else {
                        currentLine.x2++;
                        currentLine.y2++;
                    }
                } else {
                    if(currentLine != null) {
                        if(currentLine.x1 != currentLine.x2) {
                            completeLines.add(currentLine);
                        }
                        currentLine = null;
                    }
                }
            }
        }
        //  Down
        for(int x = 1; x < width-1; x++) {
            Line currentLine = null;
            for(int y = 1; y < height; y++) {
                if(boundaryMatrix[x][y] == 1) {
                    if(currentLine == null) {
                        currentLine = new Line(x, y, x, y);
                    } else {
                        currentLine.y2++;
                    }
                } else {
                    if(currentLine != null) {
                        if(currentLine.y1 != currentLine.y2) {
                            completeLines.add(currentLine);
                        }
                        currentLine = null;
                    }
                }
            }
        }
        //  Down-left
        //   We do this like the Down-right lines
        //   First we tackle the top-left 'triangle'
        for(int a = 2; a < width-1; a++) {
            Line currentLine = null;
            for(int x = a, y = 0; x > -1 && y < height; x--, y++) {
                if(boundaryMatrix[x][y] == 1) {
                    if(currentLine == null) {
                        currentLine = new Line(x, y, x, y);
                    } else {
                        currentLine.x2--;
                        currentLine.y2++;
                    }
                } else {
                    if(currentLine != null) {
                        if(currentLine.x1 != currentLine.x2) {
                            // We don't add length 1 lines
                            completeLines.add(currentLine);
                        }
                        currentLine = null;
                    }
                }
            }
        }
        //   then the bottom-right 'triangle'
        for(int a = 0; a < height-2; a++) {
            Line currentLine = null;
            for(int x = width-1, y = 0; x > -1 && y < height; x--, y++) {
                if(boundaryMatrix[x][y] == 1) {
                    if(currentLine == null) {
                        currentLine = new Line(x, y, x, y);
                    } else {
                        currentLine.x2--;
                        currentLine.y2++;
                    }
                } else {
                    if(currentLine != null) {
                        if(currentLine.x1 != currentLine.x2) {
                            completeLines.add(currentLine);
                        }
                        currentLine = null;
                    }
                }
            }
        }
        
        // Combine complete lines into polygons
        this.boundaryPolygons = new HashSet<Polygon>();
        while(!completeLines.isEmpty()) {
            Line firstLine = completeLines.remove(0);
            int startX = firstLine.x1, startY = firstLine.y1;
            int currentX = firstLine.x2, currentY = firstLine.x2;
            Polygon polygon = new Polygon();
            polygon.addPoint(startX, startY);
            boolean found = true;
            while((startX != currentX || startY != currentY) && found) {
                found = false;
                int i = 0;
                while(!found && i < completeLines.size()) {
                    Line testLine = completeLines.get(i);
                    if(testLine.x1 == currentX && testLine.y1 == currentY) {
                        completeLines.remove(i);
                        polygon.addPoint(currentX, currentY);
                        currentX = testLine.x1;
                        currentY = testLine.y1;
                        found = true;
                    } else if(testLine.x2 == currentX && testLine.y2 == currentY) {
                        completeLines.remove(i);
                        polygon.addPoint(currentX, currentY);
                        currentX = testLine.x2;
                        currentY = testLine.y2;
                        found = true;
                    }
                    i++;
                }
            }
            this.boundaryPolygons.add(polygon);
        }
    }
    
    class Line {
        public int x1, y1, x2, y2;
        
        public Line(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }
    
    public BufferedImage getShipImage() {
        return this.shipImage;
    }
    
    public BufferedImage getTransparencyImage() {
        return this.transparencyImage;
    }
    
    public BufferedImage getBoundaryImage() {
        return this.boundaryImage;
    }
    
    public Set<Polygon> getBoundaryPolygons() {
        return this.boundaryPolygons;
    }
    
    public Polygon getBoundaryPolygon() {
        return this.boundaryPolygon;
    }
}
