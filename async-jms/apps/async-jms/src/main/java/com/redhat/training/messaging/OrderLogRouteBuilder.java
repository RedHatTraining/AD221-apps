package com.redhat.training.messaging;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class OrderLogRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		// TODO receive messages from Log_Orders queue and send to the mock fulfillment end point

		}

}
