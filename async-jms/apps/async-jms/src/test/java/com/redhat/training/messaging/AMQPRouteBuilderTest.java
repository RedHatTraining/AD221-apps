package com.redhat.training.messaging;

import static com.redhat.training.messaging.Application.OrderProducer;
import com.redhat.training.model.Order;

import org.apache.camel.ProducerTemplate;
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



@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = {Application.class},
    properties = { "camel.springboot.java-routes-include-pattern=**/AMQPRouteBuilder*"})
public class AMQPRouteBuilderTest {

     private static final String MOCK_RESULT = "mock:result";
     
    @EndpointInject(uri = MOCK_RESULT)
    private MockEndpoint resultEndpoint;

    @Autowired
    private CamelContext camelContext;

    @Autowired
    private ProducerTemplate producerTemplate;

    @Before
    public void setup() throws Exception {

	camelContext.getRouteDefinition(AMQPRouteBuilder.ROUTE_NAME)
		.autoStartup(true)
                .adviceWith(camelContext, new AdviceWithRouteBuilder() {
                    @Override
                    public void configure() throws Exception {
                        interceptSendToEndpoint("direct:log_orders")
                                .skipSendToOriginalEndpoint()
                                .to(MOCK_RESULT);
                    }
                });
    }

    @Test
    public void testLogOrderRoute() throws Exception {
        // Sets an assertion

        String exectedJson = "{\"ID\":5,\"Discount\":0.012,\"Delivered\":false,\"Desc\":\"Test Order\"}";

        resultEndpoint.expectedBodiesReceived(exectedJson);

        // Sends a message to the start component
        producerTemplate.sendBody("amqp:queue:amqp_order_input", exectedJson);

        // Verifies that the mock component received 1 message
        resultEndpoint.assertIsSatisfied();
    }

}
