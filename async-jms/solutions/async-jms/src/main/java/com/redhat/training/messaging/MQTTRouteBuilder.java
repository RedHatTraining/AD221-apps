package com.redhat.training.messaging;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


@Component
public class MQTTRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		from("mqtt:topic?host=tcp://127.0.0.1:61616&userName=admin&password=admin&subscribeTopicNames=MQTT_Topic")
			.routeId("Receiving Orders via MQTT")
			.log("Passing through MQTTRouteBuilder")
			.to("direct:jsonOrderLog");
	}
}
