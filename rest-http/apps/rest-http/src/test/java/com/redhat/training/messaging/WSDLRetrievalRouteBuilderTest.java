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
    properties = { "camel.springboot.java-routes-include-pattern=**/WSDL*"})
public class WSDLRetrievalRouteBuilderTest {

    @Autowired
    private ProducerTemplate producerTemplate;

    @EndpointInject(uri = "mock:test_execution_target")
    private MockEndpoint mockEndpoint;

    @Test
    public void testWSDLRetrievalRoute() throws Exception {
        // Sets an assertion
        mockEndpoint.expectedMessageCount(1);

        // Sends a message to the start component
        producerTemplate.sendBody("direct:get-wsdl", null);

        // Verifies that the mock component received 1 message
        mockEndpoint.assertIsSatisfied();
    }
}