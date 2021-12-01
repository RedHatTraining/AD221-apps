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
                .process( new Processor() {
                    public void process( Exchange exchange ) {
                        String inputMessage = exchange.getIn().getBody( String.class );

                        AtomicReference<Long> counter = new AtomicReference<>(1L);

                        String processedLines = Stream.of(inputMessage.split(separator))
                            .map( l -> counter.getAndUpdate( p -> p + 1 ).toString() + "," + l )
                            .collect(Collectors.joining(separator));

                        exchange.getIn().setBody( processedLines );
                    }
                } )
                .to( "file:orders/outgoing?fileName=orders2.csv" );
    }
}
