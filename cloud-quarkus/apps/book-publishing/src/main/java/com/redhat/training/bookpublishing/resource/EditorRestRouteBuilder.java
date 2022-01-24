package com.redhat.training.bookpublishing.resource;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EditorRestRouteBuilder extends RouteBuilder {

    private final List<Object> inMemoryBooksForEditor = new ArrayList<>();

    @Override
    public void configure() throws Exception {
        restConfiguration().bindingMode(RestBindingMode.json);

        // TODO: Add a route to store all editor books in the local variable

        // TODO: Add a REST route to expose the books stored in the local variable
    }
}


