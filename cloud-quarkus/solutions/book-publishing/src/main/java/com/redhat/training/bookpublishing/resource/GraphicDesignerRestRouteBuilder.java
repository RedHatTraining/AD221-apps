package com.redhat.training.bookpublishing.resource;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GraphicDesignerRestRouteBuilder extends RouteBuilder {

    private final List<Object> inMemoryBooksForGraphicDesigner = new ArrayList<>();

    @Override
    public void configure() throws Exception {
        restConfiguration().bindingMode(RestBindingMode.json);

        from("file://data/pipeline/graphic-designer?noop=true")
            .routeId("pipeline-graphic-designer")
            .log("Processing file: ${header.CamelFileName}")
            .unmarshal().jacksonxml()
            .process().body(Object.class, (Consumer<Object>) inMemoryBooksForGraphicDesigner::add);

        rest("/pipeline/graphic-designer")
            .get()
            .route()
                .routeId("rest-pipeline-graphic-designer")
                .setBody(exchange -> inMemoryBooksForGraphicDesigner)
            .endRest();
    }
}


