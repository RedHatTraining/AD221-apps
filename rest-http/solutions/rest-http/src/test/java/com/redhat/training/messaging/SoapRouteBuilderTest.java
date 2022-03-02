package com.redhat.training.messaging;

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
    properties = { "camel.springboot.java-routes-include-pattern=**/SoapRoute*"})
public class SoapRouteBuilderTest {


    private static final String MOCK_RESULT_LOG = "mock:result_log";

    @EndpointInject(uri = MOCK_RESULT_LOG)
    private MockEndpoint resultLogEndpoint;

    @Autowired
    private CamelContext camelContext;

    @Autowired
    private ProducerTemplate producerTemplate;

    @Before
    public void setup() throws Exception {

	camelContext.getRouteDefinition(SoapRouteBuilder.ROUTE_NAME)
		.autoStartup(true)
                .adviceWith(camelContext, new AdviceWithRouteBuilder() {
                    @Override
                    public void configure() throws Exception {
                        interceptSendToEndpoint("direct:log_orders")
                                .skipSendToOriginalEndpoint()
                                .to(MOCK_RESULT_LOG);
                    }
                });
    }

    @Test
    public void testSaopRoute() throws Exception {

	resultLogEndpoint.expectedMessageCount(1);

        String testOrder = "{\"ID\":5,\"Discount\":0.012,\"Delivered\":false,\"Desc\":\"Test Order 1\",\"Name\":\"customer-a\"}";

        // Sends messages to the start component
        producerTemplate.sendBody("direct:soap", testOrder);

        // Verifies that a message received
	    resultLogEndpoint.assertIsSatisfied();
    }

}