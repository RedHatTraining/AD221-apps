package com.redhat.training.messaging;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


@Component
public class AMQPRouteBuilder extends RouteBuilder {

	public static String ROUTE_NAME = "amqp-order-input";

	@Override
	public void configure() throws Exception {

		// TODO: receive messages from AMQP Queue and send  to the log-orders route

	}
}
