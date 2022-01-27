package com.redhat.training.messaging;

import static com.redhat.training.messaging.Application.OrderProducer;
import com.redhat.training.model.Order;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.EndpointInject;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = {Application.class},
    properties = { "camel.springboot.java-routes-include-pattern=**/AMQPRouteBuilder*"})
public class AMQPRouteBuilderTest {

    @Autowired
    private ProducerTemplate producerTemplate;

    @EndpointInject(uri = "mock:testAMQPRouteBuilder")
    private MockEndpoint mock;

    @Test
    public void testLogOrderRoute() throws Exception {
        // Sets an assertion

        String exectedJson = "{\"ID\":5,\"Discount\":0.012,\"Delivered\":false,\"Desc\":\"Test Order\"}";

        mock.expectedBodiesReceived(exectedJson);

        // Sends a message to the start component
        producerTemplate.sendBody("amqp:queue:AMQP_Queue", exectedJson);

        // Verifies that the mock component received 1 message
        mock.assertIsSatisfied();
    }

}
