package com.redhat.training.rest;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;
@Component
public class RestRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // TODO: configure rest-dsl
        // to use servlet component and run on port 8080

        // TODO: rest services under the payments context-path
		
		// TODO: routes that implement the REST services
    }
}
