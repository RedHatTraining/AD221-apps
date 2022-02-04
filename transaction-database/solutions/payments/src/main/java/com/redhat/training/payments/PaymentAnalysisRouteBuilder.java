package com.redhat.training.payments;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class PaymentAnalysisRouteBuilder extends RouteBuilder {

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
            .to("sql:update payment_analysis "
                + "set fraud_score =:#${headers.fraudScore}, analysis_status = 'Completed' "
                + "where payment_id=:#${body.id}")
            .to("direct:payment_analysis_complete?failIfNoConsumers=false&block=false");
    }
}
