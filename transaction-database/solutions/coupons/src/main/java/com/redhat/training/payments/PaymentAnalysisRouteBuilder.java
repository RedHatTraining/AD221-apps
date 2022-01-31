package com.redhat.training.payments;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

// TODO: Enable the route by extending the RouteBuilder superclass
@Component
public class PaymentAnalysisRouteBuilder extends RouteBuilder {

    // TODO: Implement the configure method
    @Override
    public void configure() throws Exception {
        from("jpa:com.redhat.training.payments.Payment?"
                + "persistenceUnit=mysql"
                + "&consumeDelete=false"
                + "&maximumResults=5"
                + "&consumer.delay=3000"
                + "&consumeLockEntity=false")
            .log("${body}")
            .process(new PaymentFraudAnalyzer())
            .to("sql:update payments set fraud_score =:#${headers.fraudScore} where id=:#${body.id}");
    }
}
