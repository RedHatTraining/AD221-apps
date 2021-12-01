package com.redhat.training.processor;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileRouteBuilder extends RouteBuilder {

    private static String separator = System.getProperty("line.separator");

    @Override
    public void configure() throws Exception {
        from( "file:orders/incoming?noop=true" )
                // .process()
                .to( "file:orders/outgoing?fileName=orders2.csv" );
    }
}
