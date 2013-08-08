package org.lsfn.console_pc;

import java.util.HashMap;
import java.util.Map;

import org.lsfn.console_pc.STS.STSdown;

import com.google.protobuf.Descriptors;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor.JavaType;
import com.google.protobuf.GeneratedMessage;

public class NestableDataBox {

    private String name;
    private Map<String, Object> values;
    private Map<String, JavaType> types;
    private Map<String, NestableDataBox> subData;
    
    public NestableDataBox(Descriptor descriptor) {
        this.name = descriptor.getName();
        this.values = new HashMap<String, Object>();
        this.types = new HashMap<String, JavaType>();
        this.subData = new HashMap<String, NestableDataBox>();
        for(FieldDescriptor field : descriptor.getFields()) {
            if(field.getJavaType() == JavaType.MESSAGE) {
                this.subData.put(field.getName(), new NestableDataBox(field.getMessageType()));
            } else {
                this.types.put(field.getFullName(), field.getJavaType());
                this.values.put(field.getFullName(), field.getDefaultValue());
            }
        }
    }
    
    public void processInput(STSdown message) {
        Map<FieldDescriptor, Object> fields = message.getAllFields();
        for(FieldDescriptor field : fields.keySet()) {
            if(this.subData.containsKey(field.getName())) {
                
            } else if(this.types.containsKey(field.getName())) {
                
            }
        }
    }
}
