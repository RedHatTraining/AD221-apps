package com.redhat.training.testkit;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileRouteBuilder extends RouteBuilder {

    public static final String ROUTE_ID = "test-kit";

    @Override
    public void configure() throws Exception {

        from("file:in")
            .routeId(ROUTE_ID)
                .log("${body}")
                    .to("file:out");
    }
}