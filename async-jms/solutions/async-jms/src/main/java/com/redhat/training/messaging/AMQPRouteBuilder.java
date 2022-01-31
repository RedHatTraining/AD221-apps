package com.redhat.training.messaging;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


@Component
public class AMQPRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		// TODO receive messages from AMQP_Queue and send  to the Log_Orders queue
		from("amqp:queue:AMQP_Queue")
			.routeId("amqp-order-input")
			.log("Passing through AMQPRouteBuilder")
			.wireTap("mock:testAMQPRouteBuilder")
			.to("amqp:queue:Log_Orders");
	}
}
