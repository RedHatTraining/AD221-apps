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
	//TODO: add properties
})

//TODO: add @MockEndpointsAndSkip annotation
public class HttpRouteBuilderTest {

	@Autowired
	private ProducerTemplate template;

	// TODO: add @EndpointInject annotation
	MockEndpoint httpClient;

	// TODO: add @EndpointInject annotation
	MockEndpoint outFile;

	@Test
	public void testFileRecievesContentFromHttpClient() throws InterruptedException {
		httpClient.whenAnyExchangeReceived(e -> {
			e.getOut().setBody("Hello, World!");
		});

		outFile.expectedMessageCount(1);
		outFile.expectedBodiesReceived("Hello, World!");

		template.sendBody("direct:start", null);

		outFile.assertIsSatisfied();
	}

}
