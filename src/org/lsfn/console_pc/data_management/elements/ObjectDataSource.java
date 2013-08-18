package org.lsfn.console_pc.data_management.elements;

public class ObjectDataSource implements DataSource {

    private Object data;
    
    public ObjectDataSource(Object data) {
        this.data = data;
    }
    
    public void setData(Object data) {
        this.data = data;
    }
    
    @Override
    public Object getData() {
        return this.data;
    }

}
