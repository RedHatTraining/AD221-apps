package com.redhat.training.messaging;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import org.apache.camel.component.jms.JmsComponent;
import javax.jms.JMSException;


@Configuration
public class MessagingConnectionFactory {

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
