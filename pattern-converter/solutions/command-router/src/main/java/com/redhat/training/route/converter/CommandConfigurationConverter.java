package com.redhat.training.route.converter;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Calendar;

import com.redhat.training.model.CommandConfiguration;
import com.redhat.training.model.CommandConfigurationCSVRecord;

import org.apache.camel.Converter;

// TODO: Add the converter annotation
@Converter
public class CommandConfigurationConverter {

    private CommandConfigurationConverter() {}

    // TODO: Add the converter annotation
    @Converter
    public final InputStream convertToCommandConfiguration(CommandConfigurationCSVRecord csvRecord){
        
        CommandConfiguration commandConfig = new CommandConfiguration();
        // TODO: Implement the converter by setting the values of CommandConfiguration object.
        commandConfig.setName(csvRecord.getName());
        commandConfig.setType(csvRecord.getType());
        commandConfig.setValue(csvRecord.getValue());
        commandConfig.setSentDate(Calendar.getInstance().getTime());

        return new ByteArrayInputStream(commandConfig.toString().getBytes());
    }
}
