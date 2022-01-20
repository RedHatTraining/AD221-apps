package com.redhat.training.bookpublishing.route;

import com.redhat.training.bookpublishing.strategy.RoutingSlipStrategy;
import org.apache.camel.builder.RouteBuilder;

public class BookReviewPipelineRouteBuilder extends RouteBuilder {
    private final String ROUTING_HEADER = "destination";

    @Override
    public void configure() throws Exception {
        // TODO: Add the book review pipeline
    }
}
