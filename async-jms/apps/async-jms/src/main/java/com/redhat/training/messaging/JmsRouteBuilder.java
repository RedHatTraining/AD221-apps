package com.redhat.training.messaging;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import org.apache.camel.model.dataformat.JsonLibrary;

@Component
public class JmsRouteBuilder extends RouteBuilder {

	public static final String ROUTE_NAME = "jms-order-input";

	@Override
	public void configure() throws Exception {

		// TODO: Process Orders

	}
}
