package com.redhat.training.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.redhat.training.model.CommandConfiguration;

@Path("/commands")
public class CommandConfigurationResource {

    @POST
    public CommandConfiguration add(String configStr) {
        return new CommandConfiguration(configStr);
    }
}