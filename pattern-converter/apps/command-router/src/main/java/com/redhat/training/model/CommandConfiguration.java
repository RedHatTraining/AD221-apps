package com.redhat.training.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommandConfiguration {

    private String name;
    private String type;
    private int value;
    private Date sentDate;
    
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
    public Date getSentDate() {
        return sentDate;
    }
    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    @Override
    public String toString() {
        return "name=" + name + "|sentDate=" + new SimpleDateFormat("dd/MM/yyyy").format(sentDate) + "|type=" + type + "|value=" + value;
    }

}
