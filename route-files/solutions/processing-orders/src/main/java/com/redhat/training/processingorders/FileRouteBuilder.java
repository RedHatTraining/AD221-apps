package com.redhat.training.processingorders;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

// TODO: Enable the route by extending the RouteBuilder superclass
@Component
public class FileRouteBuilder extends RouteBuilder {

    // TODO: Implement the configure method
    @Override
    public void configure() throws Exception {
        from(
            "ftp://localhost:21721/?" +
            "username=datauser&password=fuse&" +
            "include=.*txt&" +
            "passiveMode=true"
        )
        .to("file:customer_requests/");
    }
}
