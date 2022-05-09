package com.redhat.training.bookpublishing.route;

import com.redhat.training.bookpublishing.strategy.RoutingSlipStrategy;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class BookReviewPipelineRouteBuilder extends RouteBuilder {
    private final String ROUTING_HEADER = "destination";

    @Override
    public void configure() throws Exception {
        // TODO: Create a route for the book review pipeline
        from("file://data/manuscripts?noop=true")
            .routeId("book-review-pipeline")
            .setHeader(ROUTING_HEADER).method(RoutingSlipStrategy.class)
            .log(String.format(
                "File: ${header.CamelFileName} - Destination: ${header.%s}",
                ROUTING_HEADER
            ))
        .routingSlip(header(ROUTING_HEADER));
    }
}
