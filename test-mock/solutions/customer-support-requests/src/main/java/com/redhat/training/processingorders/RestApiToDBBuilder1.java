package com.redhat.training.processingorders;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class RestApiToDBBuilder1 extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("stub:http://localhost/reservations/today")
            .to("mock:result");
    }
}



