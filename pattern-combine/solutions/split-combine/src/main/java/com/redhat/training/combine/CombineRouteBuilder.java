package com.redhat.training.combine;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.springframework.stereotype.Component;

@Component
public class CombineRouteBuilder extends RouteBuilder {

    private static final int BATCH_COMPLETION_INTERVAL = 10;
    private static String separator = System.getProperty( "line.separator" );

    @Override
    public void configure() throws Exception {
        from( "file:orders/incoming?noop=true" )
            .split( body().tokenize( separator ) )
            .aggregate( constant(true), new AggregationStrategy() {
                public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
                    if (oldExchange == null) {
                        return newExchange;
                    }
            
                    String oldBody = oldExchange.getIn().getBody(String.class);
                    String newBody = newExchange.getIn().getBody(String.class);
                    oldExchange.getIn().setBody(oldBody + separator + newBody);
                    return oldExchange;
                }
            } )
            .completionSize( BATCH_COMPLETION_INTERVAL )
            .to( "file:orders/outgoing?fileName=orders2.csv" );
    }
}
