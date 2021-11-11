package com.redhat.training.processingorders;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//TODO: Enable the route by extending the RouteBuilder superclass
@Component
public class FileRouteBuilder extends RouteBuilder {

    //TODO: Implement the configure method
    @Override
    public void configure() throws Exception {

        from("file:orders/incoming?include=order.*xml")
                .to("file:orders/outgoing/?fileExist=Fail");
    }
}
