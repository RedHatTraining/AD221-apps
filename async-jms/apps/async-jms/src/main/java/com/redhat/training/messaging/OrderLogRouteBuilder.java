package com.redhat.training.messaging;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class OrderLogRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("jms:queue:Log_Orders")
			.routeId("Log Orders")
			.log("Order received: ${body}")
			.to("mock:fulfillmentSystem");
	}

}
