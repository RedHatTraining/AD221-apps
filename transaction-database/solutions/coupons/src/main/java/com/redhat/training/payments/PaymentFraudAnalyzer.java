package com.redhat.training.payments;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;


/**
 * Mock fraud detector
 */
public class PaymentFraudAnalyzer implements Processor {

    @Override
    public void process( Exchange exchange ) throws Exception {
        Double fraudScore = 0.0;

        Payment payment = exchange.getIn().getBody( Payment.class );
        String email = payment.getEmail();
        Double amount = payment.getAmount();

        if ( email.length() > 10 ) {
            fraudScore += email.length() * 0.09;
        }

        if (amount > 1000) {
            fraudScore += amount * 0.00007;
        }

        if (email.contains("offer")) {
            fraudScore += 40;
        }

        if (email.contains("deal")) {
            fraudScore += 40;
        }

        fraudScore = Math.min(100.0, fraudScore) / 100;

        exchange.getIn().setHeader("fraudScore", fraudScore);
    }
}
