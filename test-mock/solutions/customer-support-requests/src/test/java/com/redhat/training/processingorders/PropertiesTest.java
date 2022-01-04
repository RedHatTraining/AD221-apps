package com.redhat.training.processingorders;

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
	"http_route.start=direct:start",
	"http_route.http_server=http://test-fake",
	"camel.springboot.java-routes-include-pattern=**/RestApiToDBBuilder4*"
})

@MockEndpointsAndSkip("http.*|file:outputhttp.*")
public class PropertiesTest {

	@Autowired
	private ProducerTemplate template;

	@EndpointInject(uri = "mock:file:outputhttp")
	MockEndpoint mockHttpOut;

	@EndpointInject(uri = "mock:http:test-fake")
	MockEndpoint mockHttpClient;


	@Test
	public void testHttpClient() throws InterruptedException {
		mockHttpClient.whenAnyExchangeReceived(e -> {
			e.getOut().setBody("Hello, World!");
		});

		mockHttpOut.expectedMessageCount(1);
		mockHttpOut.expectedBodiesReceived("Hello, World!");

		template.sendBody("direct:start", null);

		mockHttpOut.assertIsSatisfied();
	}

}
