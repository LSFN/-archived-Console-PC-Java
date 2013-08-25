package org.lsfn.console_pc.ship_designer;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ShipDesignData {
    
    private BufferedImage shipImage;
    
    public ShipDesignData(BufferedImage shipImage) {
        this.shipImage = shipImage;
    }
    
    private void computeHull() {
        int width = shipImage.getWidth();
        int height = shipImage.getHeight();
        
        // Compute the matrix of opaque pixels versus those that are partially / fully transparent
        int transparencyMatrix[][] = new int[width][height];
        WritableRaster alphaRaster = shipImage.getAlphaRaster();
        int temp[] = new int[0];
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                if(alphaRaster.getPixel(x, y, temp)[0] == 255) {
                    transparencyMatrix[x][y] = 0;
                } else {
                    transparencyMatrix[x][y] = 1;
                }
            }
        }
        
        // Compute the matrix of transparent / opaque boundary pixels
        int boundaryMatrix[][] = new int[width][height];
        for(int x = 1; x < width-1; x++) {
            for(int y = 1; y < height-1; y++) {
                if(transparencyMatrix[x][y] == 1 && (transparencyMatrix[x][y+1] == 0 || transparencyMatrix[x][y-1] == 0 ||
                        transparencyMatrix[x+1][y] == 0 || transparencyMatrix[x-1][y] == 0)) {
                    boundaryMatrix[x][y] = 1;
                } else {
                    boundaryMatrix[x][y] = 0;
                }
            }
        }
        
        // Compute complete lines from boundary
        Set<Line> completeLines = new HashSet<Line>();
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
}
