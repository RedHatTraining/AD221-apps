package com.redhat.training.transform;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Bean;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;
import javax.jms.JMSException;
import org.apache.camel.model.dataformat.XmlJsonDataFormat;

//TODO: Enable the route by extending the RouteBuilder superclass
@Component
public class TransformRouteBuilder extends RouteBuilder {

    //TODO add the XmlJsonDataFormat
    XmlJsonDataFormat xmlJson = new XmlJsonDataFormat();

    @Override
    public void configure() throws Exception {

        from("jms:queue:orderInput")
            .routeId("Transforming Orders")
            .marshal().jaxb()
            .log("XML Body: ${body}")
            //Marshal JSON
            .marshal(xmlJson)
            .log("JSON Body: ${body}")
            //Filter JSON
            .filter().jsonpath("$[?(@.delivered !='true')]")
            // Wire tap undelivered messages
            .wireTap("direct:jsonOrderLog")
            .to("mock:fufillmentSystem");

        //TODO add direct route to mock order log end point
        from("direct:jsonOrderLog")
            .routeId("Log Orders")
            .log("Order received: ${body}")
            .to("mock:orderLog");
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
