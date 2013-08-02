package org.lsfn.console_pc;

import org.lsfn.console_pc.STS.STSup;

public class PilotingData {

    /* Key state array indices correspondence chart:
    0: Turn Anti-clockwise
    1: Turn Clockwise
    2: Strafe Left
    3: Strafe Right
    4: Forward Thrust
    5: Backwards Thrust
    */
    private boolean[] desiredCommandStates;
    private boolean[] desiredCommandStatesChanged;
    
    public PilotingData() {
        this.desiredCommandStates = new boolean[6];
        for(int i = 0; i < this.desiredCommandStates.length; i++) {
            this.desiredCommandStates[i] = false;
        }
        this.desiredCommandStatesChanged = new boolean[6];
        for(int i = 0; i < this.desiredCommandStatesChanged.length; i++) {
            this.desiredCommandStatesChanged[i] = false;
        }
    }
    
    public void setTurnAnti(boolean state) {
        this.desiredCommandStates[0] = state;
        this.desiredCommandStatesChanged[0] = true;
    }
    
    public void setTurnClock(boolean state) {
        this.desiredCommandStates[1] = state;
        this.desiredCommandStatesChanged[1] = true;
    }
    
    public void setThrustLeft(boolean state) {
        this.desiredCommandStates[2] = state;
        this.desiredCommandStatesChanged[2] = true;
    }
    
    public void setThrustRight(boolean state) {
        this.desiredCommandStates[3] = state;
        this.desiredCommandStatesChanged[3] = true;
    }
    
    public void setThrustForward(boolean state) {
        this.desiredCommandStates[4] = state;
        this.desiredCommandStatesChanged[4] = true;
    }
    
    public void setThrustBackward(boolean state) {
        this.desiredCommandStates[5] = state;
        this.desiredCommandStatesChanged[5] = true;
    }
    
    public STSup.Piloting generateOutput() {
        boolean pilotingStatesChanged = false;
        for(int i = 0; i < this.desiredCommandStatesChanged.length; i++) {
            pilotingStatesChanged |= this.desiredCommandStatesChanged[i];
        }
        if(pilotingStatesChanged) {
            STSup.Piloting.Builder stsUpPiloting = STSup.Piloting.newBuilder();
            if(this.desiredCommandStatesChanged[0]) {
                stsUpPiloting.setTurnAnti(this.desiredCommandStates[0]);
                this.desiredCommandStatesChanged[0] = false;
            }
            if(this.desiredCommandStatesChanged[1]) {
                stsUpPiloting.setTurnClock(this.desiredCommandStates[1]);
                this.desiredCommandStatesChanged[1] = false;
            }
            if(this.desiredCommandStatesChanged[2]) {
                stsUpPiloting.setThrustLeft(this.desiredCommandStates[2]);
                this.desiredCommandStatesChanged[2] = false;
            }
            if(this.desiredCommandStatesChanged[3]) {
                stsUpPiloting.setThrustRight(this.desiredCommandStates[3]);
                this.desiredCommandStatesChanged[3] = false;
            }
            if(this.desiredCommandStatesChanged[4]) {
                stsUpPiloting.setThrustForward(this.desiredCommandStates[4]);
                this.desiredCommandStatesChanged[4] = false;
            }
            if(this.desiredCommandStatesChanged[5]) {
                stsUpPiloting.setThrustBackward(this.desiredCommandStates[5]);
                this.desiredCommandStatesChanged[5] = false;
            }
            return stsUpPiloting.build();
        }
        return null;
    }
}
