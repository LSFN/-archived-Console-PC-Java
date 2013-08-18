package org.lsfn.console_pc.data_management.elements;

public class StringDataSource implements DataSource {

    private String data;
    
    public StringDataSource(String data) {
        this.data = data;
    }
    
    public void setData(String data) {
        this.data = data;
    }
    
    @Override
    public String getData() {
        return this.data;
    }

}
