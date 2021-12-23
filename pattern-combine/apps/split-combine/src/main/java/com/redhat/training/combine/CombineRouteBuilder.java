package com.redhat.training.combine;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CombineRouteBuilder extends RouteBuilder {

    private static final int BATCH_COMPLETION_INTERVAL = 10;
    private static String separator = System.getProperty("line.separator");

    @Override
    public void configure() throws Exception {
        from( "file:orders/incoming?noop=true" )
                // Split and Aggregate
                .to( "file:orders/outgoing?fileName=orders2.csv" );
    }
}
