package org.lsfn.console_pc.data_management.elements;

public class BooleanDataSource implements DataSource {

    private Boolean data;
    
    public BooleanDataSource(Boolean data) {
        this.data = data;
    }
    
    public void setData(Boolean data) {
        this.data = data;
    }
    
    @Override
    public Boolean getData() {
        return this.data;
    }

}
