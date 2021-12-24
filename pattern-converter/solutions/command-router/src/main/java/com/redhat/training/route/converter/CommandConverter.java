package com.redhat.training.route.converter;

import com.redhat.training.model.Command;

import org.apache.camel.Converter;

@Converter
public class CommandConverter {

    @Converter
    public Command convertToCommand(String object){
        Command command = new Command();
        return command;
    }
    
}
