package com.redhat.training.transform;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Bean;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;
import javax.jms.JMSException;

//import org.apache.camel.model.dataformat.XmlJsonDataFormat;

@Component
public class TransformRouteBuilder extends RouteBuilder {

    //TODO add the XmlJsonDataFormat

    @Override
    public void configure() throws Exception {

        from("jms:queue:orderInput")
            .routeId("Transforming Orders")
            .marshal().jaxb()
            .log("XML Body: ${body}")
            .to("mock:fufillmentSystem");

        //TODO add direct route to mock order log end point
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
