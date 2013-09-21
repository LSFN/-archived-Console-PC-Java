package org.lsfn.console_pc.data_store.sources;

public class SourceString implements ISourceString {

	private String data;
	
	public SourceString(String data) {
		this.data = data;
	}

	@Override
	public void appendCharacter(char c) {
		this.data += c;
	}

	@Override
	public void deleteCharacter() {
		if(this.data.length() > 0) {
			this.data = this.data.substring(0, this.data.length() - 1);
		}
	}
}
