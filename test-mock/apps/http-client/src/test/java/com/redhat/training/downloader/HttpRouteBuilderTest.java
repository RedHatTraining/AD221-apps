package com.redhat.training.downloader;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith( CamelSpringBootRunner.class )
@SpringBootTest(properties = {
    // TODO: add properties
})

// TODO: add @MockEndpointsAndSkip annotation
public class HttpRouteBuilderTest {

    @Autowired
    private ProducerTemplate template;

    // TODO: add @EndpointInject annotation
    MockEndpoint httpMockEndpoint;

    // TODO: add @EndpointInject annotation
    MockEndpoint fileMockEndpoint;

    @Test
    public void testFileRecievesContentFromHttpClient() throws InterruptedException {
        // TODO: add httpMockEndpoint behaviour

        // TODO: add fileMockEndpoint expectations

        template.sendBody("direct:start", null);

        // TODO: assert fileMockEndpoint
    }
}
