package com.redhat.training.payments.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import com.redhat.training.payments.model.Payment;


public class NotificationProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        Payment payment = exchange.getIn().getBody(Payment.class);

        if (payment.getOrderId() == null || payment.getOrderId() <= 0) {
            throw new IllegalStateException();
        }

        if (payment.getEmail() == null || payment.getEmail().length() == 0) {
            throw new InvalidEmailException();
        }

        payment.markAsNotified();

        exchange.getOut().setBody(payment, Payment.class);
    }
}
