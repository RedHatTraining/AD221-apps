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

import java.math.BigDecimal;
import java.lang.Integer;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = {Application.class},
    properties = { "camel.springboot.java-routes-include-pattern=**/JmsRoute*"})
public class JmsRouteBuilderTest {

    @Autowired
    private ProducerTemplate producerTemplate;

    @EndpointInject(uri = "mock:testJmsRouteBuilder")
    private MockEndpoint mock;

    @Test
    public void testLogOrderRoute() throws Exception {
        // Sets an assertion

        String exectedJson = "{\"ID\":5,\"Discount\":0.012,\"Delivered\":false,\"Desc\":\"Test Order\"}";

        mock.expectedBodiesReceived(exectedJson);

        // Builds sample test data
        OrderProducer orderProducer  = new OrderProducer(
			new Integer(5),
			new BigDecimal("0.012"),
			false,
            "Test Order"
        );

        Order testOrder = orderProducer.getOrder();

        // Sends a message to the start component
        producerTemplate.sendBody("jms:queue:orderInput", testOrder);

        // Verifies that the mock component received 1 message
        mock.assertIsSatisfied();
    }

}
