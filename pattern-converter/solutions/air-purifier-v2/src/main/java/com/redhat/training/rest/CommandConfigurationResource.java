package com.redhat.training.rest;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.redhat.training.model.CommandConfiguration;

@Path("/commands")
public class CommandConfigurationResource {

    private Set<CommandConfiguration> commandSet = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    @POST
    public Set<CommandConfiguration> add(String configStr) {
        commandSet.add(new CommandConfiguration(configStr));
        return commandSet;
    }
}