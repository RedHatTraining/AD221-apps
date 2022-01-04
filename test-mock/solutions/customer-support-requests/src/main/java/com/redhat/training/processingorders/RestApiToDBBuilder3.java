package com.redhat.training.processingorders;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class RestApiToDBBuilder3 extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:b")
            .to("file:output?fileName=result.txt");
    }
}

