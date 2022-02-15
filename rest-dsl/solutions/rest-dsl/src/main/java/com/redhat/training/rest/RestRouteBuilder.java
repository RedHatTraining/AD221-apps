package com.redhat.training.rest;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;
@Component
public class RestRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // configure rest-dsl
        restConfiguration()
           	// to use servlet component and run on port 8080
            .component("servlet")
			.port(8080)
			.bindingMode(RestBindingMode.json);

        // rest services under the payments context-path
        rest("/payments")
            .get("/{userId}")
                .to("direct:getPayment")
            .get("/")
                .to("direct:allPayments");
		
		// routes that implement the REST services
		from("direct:getPayment")
			.log("Retrieving payment with id ${header.userId}")
			.toD("jpa:com.redhat.training.payments.Payment?query=select p from com.redhat.training.rest.Payment p where p.userId = ${header.userId}");
		
		from("direct:allPayments")
			.log("Retrieving all payments")
			.to("jpa:com.redhat.training.payments.Payment?query=select p from com.redhat.training.rest.Payment p");
    }
}
