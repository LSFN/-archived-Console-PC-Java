package org.lsfn.console_pc;

import java.awt.Graphics2D;

public class SpaceObject {

    private double x, y, theta;
    
    public enum ObjectType {
        SHIP,
        ASTEROID
    }
    private ObjectType objectType;
    
    public SpaceObject(ObjectType objectType, double x, double y, double theta) {
        this.objectType = objectType;
        this.x = x;
        this.y = y;
        this.theta = theta;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getTheta() {
        return theta;
    }

    public ObjectType getObjectType() {
        return objectType;
    }
    
}
