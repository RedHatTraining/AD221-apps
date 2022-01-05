package com.redhat.training.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

public class CommandConfiguration {

    private String name;
    private String type;
    private int value;
    private Date sentDate;

    public CommandConfiguration(String configStr) {
        if(!configStr.contains("|") || !configStr.contains("=")){
            throw new RuntimeException("Format not supported");
        }

        Stream.of(configStr.split("\\|"))
        .map(s -> s.split("="))
        .forEach(a -> {
            if (a[0].equals("name")) name = a[1];
            if (a[0].equals("type")) type = a[1];
            if (a[0].equals("value")) value = Integer.parseInt(a[1]);
            if (a[0].equals("sentDate"))
                try {
                    sentDate = new SimpleDateFormat("dd/MM/yyyy").parse(a[1]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
        });
    }

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
    

    
    
}
