package com.redhat.training.processor;

import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
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
