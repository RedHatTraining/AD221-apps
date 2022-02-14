package com.redhat.training.payments.connection;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.JMSException;

@Configuration
public class PaymentsConnectionFactory {
    @Bean
    public JmsComponent jmsComponent() throws JMSException {
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        cf.setBrokerURL("tcp://localhost:61616");
        cf.setUser("admin");
        cf.setPassword("admin");

        JmsComponent jms = new JmsComponent();
        jms.setConnectionFactory(cf);

        return jms;
    }
}
