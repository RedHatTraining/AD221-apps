package com.redhat.training.model;

import java.io.Serializable;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator=",")
public class CommandConfigurationCSVRecord implements Serializable {

    private static final long serialVersionUID = -1L;

    @DataField(pos=1)
    private String name;

    @DataField(pos=2)
    private String type;

    @DataField(pos=3)
    private int value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    
}
