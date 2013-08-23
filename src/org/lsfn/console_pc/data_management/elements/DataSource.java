package org.lsfn.console_pc.data_management.elements;

/**
 * Classes implement this interface to allow widgets to
 * access the data and then display it.
 * @author LukeusMaximus
 *
 */
public interface DataSource {
    
    /**
     * Returns the data being held in this data source.
     * @return The data.
     */
    public Object getData();
    
}
