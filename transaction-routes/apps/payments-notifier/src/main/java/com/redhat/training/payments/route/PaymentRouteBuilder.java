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

        // TODO: Create a route to process payment files

    }
}
