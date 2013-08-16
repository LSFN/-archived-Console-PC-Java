package org.lsfn.console_pc.data_management;

import java.util.HashSet;
import java.util.Set;

import org.lsfn.console_pc.STS.STSdown;
import org.lsfn.console_pc.STS.STSdown.VisualSensors;
import org.lsfn.console_pc.data_management.SpaceObject.ObjectType;


public class VisualSensorsData {

    private Set<SpaceObject> visualSensorsSpaceObjects;
    
    public VisualSensorsData() {
        this.visualSensorsSpaceObjects = new HashSet<SpaceObject>();
    }
    
    public Set<SpaceObject> getVisualSensorsSpaceObjects() {
        return visualSensorsSpaceObjects;
    }

    public void processVisualSensors(STSdown.VisualSensors visualSensors) {
        this.visualSensorsSpaceObjects.clear();
        for(STSdown.VisualSensors.SpaceObject so : visualSensors.getSpaceObjectsList()) {
            SpaceObject.ObjectType objType = ObjectType.SHIP;
            if(so.getType() == VisualSensors.SpaceObject.Type.ASTEROID) {
                objType = ObjectType.ASTEROID;
            }
            this.visualSensorsSpaceObjects.add(new SpaceObject(objType, so.getPosition().getX(), so.getPosition().getY(), so.getOrientation()));
        }
    }
}
