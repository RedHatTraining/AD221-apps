package com.redhat.training.rest;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.redhat.training.model.Command;

@Path("/commands")
public class CommandResource {

    private Set<Command> commandSet = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    @POST
    public Set<Command> add(Command command) {
        commandSet.add(command);
        return commandSet;
    }
}