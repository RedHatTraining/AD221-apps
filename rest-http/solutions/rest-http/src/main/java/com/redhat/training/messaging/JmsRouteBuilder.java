package com.redhat.training.messaging;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import org.apache.camel.model.dataformat.JsonLibrary;

@Component
public class JmsRouteBuilder extends RouteBuilder {

	public static final String ROUTE_NAME = "jms-order-input";

	@Override
	public void configure() throws Exception {

		from("jms:queue:jms_order_input")
			.routeId(ROUTE_NAME)
			.marshal().json(JsonLibrary.Jackson)
			.log("JSON Body from JMSRouteBuilder: ${body}")
			.choice()
				.when(jsonpath("$[?(@.Delivered == false)]"))
					.to("direct:log_orders")
				.when(jsonpath("$[?(@.Delivered == true)]"))
					.to("direct:enrich");
	}
}
