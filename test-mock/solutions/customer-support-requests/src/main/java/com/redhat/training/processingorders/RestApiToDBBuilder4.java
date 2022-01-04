package com.redhat.training.processingorders;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class RestApiToDBBuilder4 extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("{{http_route.start}}")
            .to("{{http_route.http_server}}")
            .to("file:outputhttp?fileName=response.txt");

    }
}

