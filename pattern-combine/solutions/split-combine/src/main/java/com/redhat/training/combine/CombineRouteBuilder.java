package com.redhat.training.combine;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.springframework.stereotype.Component;

@Component
public class CombineRouteBuilder extends RouteBuilder {

    private static final String ROUTE_ID = "split-combine-pipeline";
    private static final String OUTPUT_FILE = "file:orders/outgoing?fileName=orders2.csv";
    private static final String INCOMING_FILE = "file:orders/incoming?noop=true";
    private static final String SEPARATOR = System.getProperty( "line.separator" );
    private static final int BATCH_COMPLETION_INTERVAL = 10;

    @Override
    public void configure() throws Exception {
        from( INCOMING_FILE )
        .routeId( ROUTE_ID )
            .split( body().tokenize( SEPARATOR ) )
            .aggregate( constant(true), new AggregationStrategy() {
                public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
                    if (oldExchange == null) {
                        return newExchange;
                    }
            
                    String oldBody = oldExchange.getIn().getBody(String.class);
                    String newBody = newExchange.getIn().getBody(String.class);
                    oldExchange.getIn().setBody(oldBody + SEPARATOR + newBody);
                    return oldExchange;
                }
            } )
            .completionSize( BATCH_COMPLETION_INTERVAL )
            .to( OUTPUT_FILE );
    }
}
