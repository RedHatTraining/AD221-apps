package com.redhat.training.rest;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;
@Component
public class RestRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // configure rest-dsl
        // to use servlet component and run on port 8080

        // rest services under the payments context-path
		
		// routes that implement the REST services
    }
}
