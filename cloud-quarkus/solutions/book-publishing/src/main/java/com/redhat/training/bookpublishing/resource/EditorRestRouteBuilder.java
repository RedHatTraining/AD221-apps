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

        // Reading from a file endpoint and storing the values into a local variable for the REST endpoint
        from("file://data/pipeline/editor?noop=true")
            .routeId("pipeline-editor")
            .log("Processing file: ${header.CamelFileName}")
            .unmarshal().jacksonxml()
            .process().body(Object.class, (Consumer<Object>) inMemoryBooksForEditor::add);

        rest("/pipeline/editor")
            .get()
            .route()
                .routeId("rest-pipeline-editor")
                .setBody(exchange -> inMemoryBooksForEditor)
            .endRest();
    }
}


