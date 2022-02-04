package com.redhat.training.payments;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class PaymentAnalysisRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // TODO: specify JPA endpoint
        from("")
            .log("${body}")
            .process(new PaymentFraudAnalyzer())
            // TODO: add SQL producer endpoint
            .to("direct:payment_analysis_complete?failIfNoConsumers=false&block=false");
    }
}
