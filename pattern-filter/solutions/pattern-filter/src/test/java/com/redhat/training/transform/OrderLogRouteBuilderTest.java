package com.redhat.training.transform;

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
    properties = { "camel.springboot.java-routes-include-pattern=**/Order*"})
public class OrderLogRouteBuilderTest {

    @Autowired
    private ProducerTemplate producerTemplate;

    @EndpointInject(uri = "mock:orderLog")
    private MockEndpoint mockOrderLog;

    @Test
    public void testLogOrderRoute() throws Exception {
        // Sets an assertion
        mockOrderLog.expectedMessageCount(1);

        // Sends a message to the start component
        producerTemplate.sendBody("direct:jsonOrderLog", null);

        // Verifies that the mock component received 1 message
        mockOrderLog.assertIsSatisfied();
    }

}