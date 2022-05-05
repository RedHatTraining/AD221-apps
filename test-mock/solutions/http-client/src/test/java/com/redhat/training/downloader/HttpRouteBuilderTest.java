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
    "http_route.start=direct:start",
    "http_route.server=http4://test-fake"
})

// TODO: add @MockEndpointsAndSkip annotation
@MockEndpointsAndSkip("http.*|file:out.*")
public class HttpRouteBuilderTest {

    @Autowired
    private ProducerTemplate template;

    // TODO: add @EndpointInject annotation
    @EndpointInject(uri = "mock:http4:test-fake/greeting")
    MockEndpoint httpMockEndpoint;

    // TODO: add @EndpointInject annotation
    @EndpointInject(uri = "mock:file:out")
    MockEndpoint fileMockEndpoint;

    @Test
    public void testFileRecievesContentFromHttpClient() throws InterruptedException {
        // TODO: add httpMockEndpoint behaviour
        httpMockEndpoint.whenAnyExchangeReceived(e -> {
            e.getOut().setBody("Hello test!");
        });

        // TODO: add fileMockEndpoint expectations
        fileMockEndpoint.expectedMessageCount(1);
        fileMockEndpoint.expectedBodiesReceived("Hello test!");

        template.sendBody("direct:start", null);

        // TODO: assert fileMockEndpoint
        fileMockEndpoint.assertIsSatisfied();
    }

}
