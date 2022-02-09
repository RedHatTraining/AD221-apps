package com.redhat.training.rest;

import javax.persistence.NonUniqueResultException;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class RestRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        onException(NonUniqueResultException.class)
			.handled(true)
			.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
			.setHeader(Exchange.CONTENT_TYPE, constant("text/plain"))
			.setBody().constant("Data error finding payment with ID!");
			
		
		// configure rest-dsl
        restConfiguration()
           	// to use spark-rest component and run on port 8080
            .component("spark-rest")
			.port(8080)
			.bindingMode(RestBindingMode.json);

        // rest services under the payments context-path
        rest("/payments")
            .get("/{id}")
                .to("direct:getPayment")
            .get("/")
                .to("direct:allPayments");
		
		// routes that implement the REST services
		from("direct:getPayment")
			.log("Retrieving payment with id ${header.id}")
			.to("jpa:com.redhat.training.payments.Payment?query=select * from payments where id  = :#id )");
		
		from("direct:allPayments")
			.log("Retrieving all payments")
			.to("jpa:com.redhat.training.payments.Payment?query=select * from payments&entityType=java.util.List");
    }
}
