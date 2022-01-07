package com.redhat.training.transform;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Bean;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;
import javax.jms.JMSException;

//TODO: Enable the route by extending the RouteBuilder superclass
@Component
public class SchedulerRouteBuilder extends RouteBuilder {

    //TODO: Implement the configure method
    @Override
    public void configure() throws Exception {

	    from("scheduler:myScheduler?delay=2000")
	    .routeId("Java DSL route")
	    .setBody().simple("Current time is ${header.CamelTimerFiredTime}")
        .log("Sending message to the body logging route")
        .to("direct:log_body")
        .to("jms:queue:orderInput");

/*       from("jms:queue:orderInput")
        .routeId("Java DSL route 2")
        .log("Pulled the message from the queue")
        .to("direct:log_body"); */

    }

    @Bean
    public JmsComponent jmsComponent() throws JMSException {
        // Create the connectionfactory which will be used to connect to Artemis
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        cf.setBrokerURL("tcp://localhost:61616");
        cf.setUser("admin");
        cf.setPassword("admin");

        // Create the Camel JMS component and wire it to our Artemis connectionfactory
        JmsComponent jms = new JmsComponent();
        jms.setConnectionFactory(cf);
        return jms;
    }
}
