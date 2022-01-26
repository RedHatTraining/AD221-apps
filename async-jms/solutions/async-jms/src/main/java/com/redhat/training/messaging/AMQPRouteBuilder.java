package com.redhat.training.messaging;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


@Component
public class AMQPRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		from("amqp:queue:AMQP_Queue")
			.routeId("Receiving Orders via AMQP")
			.log("Passing through AMQPRouteBuilder")
			.to("amqp:topic:MQTT_Topic?receiveTimeout=5000&allowNullBody=false");
	}
}
