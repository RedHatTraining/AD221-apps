package com.redhat.training.messaging;

import static com.redhat.training.messaging.Application.OrderProducer;
import com.redhat.training.model.Order;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.EndpointInject;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.lang.Integer;


@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = {Application.class},
    properties = { "camel.springboot.java-routes-include-pattern=**/JmsRoute*"})
public class JmsRouteBuilderTest {


    private static final String MOCK_RESULT_LOG = "mock:result_log";
    private static final String MOCK_RESULT_AMQP = "mock:result_amqp";

    @EndpointInject(uri = MOCK_RESULT_LOG)
    private MockEndpoint resultLogEndpoint;

    @EndpointInject(uri = MOCK_RESULT_AMQP)
    private MockEndpoint resultAMQPEndpoint;

    @Autowired
    private CamelContext camelContext;

    @Autowired
    private ProducerTemplate producerTemplate;

    @Before
    public void setup() throws Exception {
	    
	camelContext.getRouteDefinition(JmsRouteBuilder.ROUTE_NAME)
		.autoStartup(true)
                .adviceWith(camelContext, new AdviceWithRouteBuilder() {
                    @Override
                    public void configure() throws Exception {
                        interceptSendToEndpoint("direct:log_orders")
                                .skipSendToOriginalEndpoint()
                                .to(MOCK_RESULT_LOG);
                        interceptSendToEndpoint("amqp:queue:amqp_order_input")
                                .skipSendToOriginalEndpoint()
                                .to(MOCK_RESULT_AMQP);
                    }
                });
    }

    @Test
    public void testJmsOrderRoute() throws Exception {

	resultLogEndpoint.expectedMessageCount(1);
    resultAMQPEndpoint.expectedMessageCount(1);

        // Builds sample test data
        OrderProducer orderProducer  = new OrderProducer(
		new Integer(5),
		new BigDecimal("0.012"),
		false,
		"Test Order 1"
        );

        OrderProducer orderProducer2  = new OrderProducer(
		new Integer(10),
		new BigDecimal("0.015"),
		true,
		"Test Order 2"
        );

        Order testOrder = orderProducer.getOrder();
        Order testOrder2 = orderProducer2.getOrder();

        // Sends messages to the start component
        producerTemplate.sendBody("jms:queue:jms_order_input", testOrder);
        producerTemplate.sendBody("jms:queue:jms_order_input", testOrder2);

        // Verifies that a message received
	    resultLogEndpoint.assertIsSatisfied();
        resultAMQPEndpoint.assertIsSatisfied();
    }

}
