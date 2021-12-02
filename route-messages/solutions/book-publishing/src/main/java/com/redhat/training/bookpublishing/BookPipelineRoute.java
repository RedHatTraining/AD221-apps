package com.redhat.training.bookpublishing;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class BookPipelineRoute extends RouteBuilder {
    private final String ROUTING_SLIP_HEADER = "destination";

    @Override
    public void configure() throws Exception {
        // TODO: Create a route for the book publishing pipeline
        from("file://data/manuscripts?noop=true")
        .routeId("book-pipeline")
        .setHeader(ROUTING_SLIP_HEADER)
            .method(RoutingSlipStrategy.class)
        .routingSlip(header(ROUTING_SLIP_HEADER));

        // TODO: Create a route for the printing pipeline
    }
}
