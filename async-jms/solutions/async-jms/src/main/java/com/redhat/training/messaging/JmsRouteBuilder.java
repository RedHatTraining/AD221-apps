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

		from("jms:queue:orderInput")
			.routeId("Receiving Orders via JMS")
			.marshal().json(JsonLibrary.Jackson)
			.log("JSON Body from JMSRoutBuilder: ${body}")
			.to("jms:queue:AMQP_Queue");
	}

	@Bean
	public JmsComponent jmsComponent() throws JMSException {
	 	// Creates the connectionfactory which will be used to connect to Artemis
	 	ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
	 	connectionFactory.setBrokerURL("tcp://localhost:61616");
	 	connectionFactory.setUser("admin");
	 	connectionFactory.setPassword("admin");

	 	// Creates the Camel JMS component and wires it to our Artemis connectionfactory
	 	JmsComponent jms = new JmsComponent();
	 	jms.setConnectionFactory(connectionFactory);

	 	return jms;
	}
}
