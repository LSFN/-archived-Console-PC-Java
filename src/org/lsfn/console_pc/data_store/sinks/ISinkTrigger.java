package org.lsfn.console_pc.data_store.sinks;

public interface ISinkTrigger {

	/**
	 * This method returns true if the trigger was triggered.
	 * The trigger event is consumed in the check so a subsequent
	 * call to isTriggered() will not represent the same trigger
	 * event but will instead consume the next trigger event.
	 * @return True if the trigger was triggered.
	 */
    public boolean isTriggered();
}
