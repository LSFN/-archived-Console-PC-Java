package org.lsfn.console_pc.data_store.sources;

public class SourceTrigger implements ISourceTrigger {

	private int numberOfTriggers;
	
	public SourceTrigger() {
		this.numberOfTriggers = 0;
	}
	
	@Override
	public void trigger() {
		numberOfTriggers++;
	}

}
