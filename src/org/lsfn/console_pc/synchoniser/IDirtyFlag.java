package org.lsfn.console_pc.synchoniser;

/**
 * This interface is used for determining if any of a class' members have been altered since the last resetFlag() call.
 * 
 * @author LukeusMaximus
 *
 */
public interface IDirtyFlag {

	/**
	 * Tells us if the class data has been altered.
	 * @return true if the data has been altered, false otherwise. 
	 */
	public boolean flagRaised();
	
	/**
	 * Resets the flag.
	 */
	public void resetFlag();
}
