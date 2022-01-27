package com.redhat.training.messaging;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Bean;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;
import javax.jms.JMSException;

import org.apache.camel.model.dataformat.JsonLibrary;

@Component
public class JmsRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		// TODO: Receive from the orderInput queue, convert to json, and send to the AMQP_Queue

	}

	// TODO: Add the connectionFactory Bean
	
}
