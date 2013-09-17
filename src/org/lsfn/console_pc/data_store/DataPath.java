package org.lsfn.console_pc.data_store;

import java.util.Stack;

public class DataPath implements IDataPath {

    private Stack<String> pathLevels;
    
    public DataPath(String path) {
        String[] split = path.split("/");
        for(int i = split.length; i > -1 ; i--) {
            this.pathLevels.push(split[i]);
        }
    }

    @Override
    public boolean topLevelMatch(String pathNode) {
        return !this.pathLevels.isEmpty() && this.pathLevels.peek().equals(pathNode);
    }

    @Override
    public IDataPath stripTopLevel() {
        if(!this.pathLevels.isEmpty()) {
            this.pathLevels.pop();
        }
        return this;
    }
    
}
