package com.redhat.training.messaging;

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
    properties = { "camel.springboot.java-routes-include-pattern=**/EnrichRoute*,**/SoapRoute*,**/OrderLogRoute*"})
public class EnrichRouteBuilderTest {


    private static final String MOCK_RESULT_LOG = "mock:result_log";

    @EndpointInject(uri = MOCK_RESULT_LOG)
    private MockEndpoint resultLogEndpoint;

    @Autowired
    private ProducerTemplate producerTemplate;


   @EndpointInject(uri = "mock:fulfillmentSystem")
    private MockEndpoint mockFulfillment;

    @Test
    public void testEnrichRoute() throws Exception {

	    mockFulfillment.expectedMessageCount(2);

        String testOrder = "{\"ID\":5,\"Discount\":0.012,\"Delivered\":false,\"Desc\":\"Test Order 1\",\"Name\":\"customer-a\"}";

        // Sends messages to the start component
        producerTemplate.sendBody("direct:enrich", testOrder);

        // Verifies that a message received
	    mockFulfillment.assertIsSatisfied();
    }

}