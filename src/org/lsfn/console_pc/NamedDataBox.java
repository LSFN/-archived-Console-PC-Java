package org.lsfn.console_pc;

import com.google.protobuf.ByteString;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Descriptors.EnumValueDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;

public class NamedDataBox {

    private String name;
    private Descriptors.FieldDescriptor.JavaType type;
    private boolean booleanData;
    private ByteString byteStringData;
    private double doubleData;
    private EnumValueDescriptor enumData;
    private float floatData;
    private int integerData;
    private long longData;
    private String stringData;

    public NamedDataBox(FieldDescriptor fieldDescriptor) {
        this.name = fieldDescriptor.getName();
        this.type = fieldDescriptor.getJavaType();
    }
    
}
