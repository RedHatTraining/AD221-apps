package com.redhat.training.payments.route;

import com.redhat.training.payments.model.Payment;
import com.redhat.training.payments.processor.InvalidEmailException;
import com.redhat.training.payments.processor.NotificationProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;

@Component
public class PaymentRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        JaxbDataFormat xmlDataFormat = new JaxbDataFormat();
        JAXBContext jaxbContext = JAXBContext.newInstance(Payment.class);
        xmlDataFormat.setContext(jaxbContext);

        // TODO: Add a dead letter queue
        onException(IllegalStateException.class, InvalidEmailException.class)
            .maximumRedeliveries(1)
            .handled(true)
            .to("jms:queue:dead-letter")
            .markRollbackOnly();

        // TODO: Set the route as transacted
        from("file://data/payments?noop=true")
            .transacted("PROPAGATION_REQUIRED")
            .routeId("payments-process")
            .log("File: ${header.CamelFileName}")
            .unmarshal(xmlDataFormat)
            .to("jpa:" + Payment.class.getName() + "&usePersist=true")
            .process(new NotificationProcessor())
            .to("jms:queue:payment-notifications");
    }
}
