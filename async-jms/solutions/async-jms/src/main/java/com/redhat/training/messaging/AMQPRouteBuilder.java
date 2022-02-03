package com.redhat.training.messaging;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


@Component
public class AMQPRouteBuilder extends RouteBuilder {

	public static String ROUTE_NAME = "amqp-order-input";

	@Override
	public void configure() throws Exception {

		// TODO: receive messages from AMQP queue and send  to the log-orders route
		from("amqp:queue:amqp_order_input")
			.routeId(ROUTE_NAME)
			.log("AMQPRouteBuilder: Processing Non-delivered Orders")
			.to("direct:log_orders");
	}
}
