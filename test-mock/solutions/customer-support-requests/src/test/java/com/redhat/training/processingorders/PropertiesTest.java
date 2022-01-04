package com.redhat.training.processingorders;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpoints;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@RunWith( CamelSpringBootRunner.class )
@SpringBootTest(properties = {
	"http_route.start=direct:start",
	"http_route.http_server=http://test-fake",
	// "routea.destination = mock:out",
	// "http_route.start=direct:scheduler:my_sch",
	// "http_route.server=stub:http://localhost:8080/",
	"camel.springboot.java-routes-include-pattern=**/RestApiToDBBuilder4*"
})

@MockEndpointsAndSkip("http.*|file:outputhttp.*")
public class PropertiesTest {

	@Autowired
	private ProducerTemplate template;

	// @EndpointInject( uri = "mock:out" )
	// MockEndpoint mockTest2;

	// @EndpointInject( uri = "mock:result" )
	// MockEndpoint mockResult;

	// @EndpointInject(uri = "mock:file:output")
	// MockEndpoint mockFileOutput;

	@EndpointInject(uri = "mock:file:outputhttp")
	MockEndpoint mockHttpOut;

	@EndpointInject(uri = "mock:http:test-fake")
	MockEndpoint mockHttpClient;

	// @Test
	// public void testUseProperties() throws InterruptedException {
	// 	mockTest2.expectedMessageCount(1);
	// 	mockTest2.expectedBodiesReceived("Hola");

	// 	template.sendBody("direct:a", "Hola");

	// 	mockTest2.assertIsSatisfied();
	// }

	// @AfterEach
	// public void afterEach() {
	// 	mockHttpOut.reset();
	// }

	// @Test
	// public void testUseProperties2() throws InterruptedException {
	// 	mockResult.expectedMessageCount(1);
	// 	mockResult.expectedBodiesReceived("Holasss");

	// 	template.sendBody("stub:http://localhost/reservations/today", "Holasss");

	// 	mockResult.assertIsSatisfied();
	// }


	// @Test
	// public void testAutomck() throws InterruptedException {
	// 	mockFileOutput.expectedMessageCount(1);
	// 	mockFileOutput.expectedBodiesReceived("Hey");

	// 	template.sendBody("direct:b", "Hey");

	// 	mockFileOutput.assertIsSatisfied();
	// }

	@Test
	public void testHttpClient() throws InterruptedException {
		// mockHttpClient.expectedMessageCount(1);

		mockHttpClient.whenAnyExchangeReceived(e -> {
			e.getOut().setBody("Hello, World!");
		});

		mockHttpOut.expectedMessageCount(1);
		mockHttpOut.expectedBodiesReceived("Hello, World!");

		template.sendBody("direct:start", null);

		mockHttpOut.assertIsSatisfied();
		// mockHttpClient.assertIsSatisfied();
	}

	// @Test
	// public void testHttpClient2() throws InterruptedException {
	// 	mockHttpOut.expectedMessageCount(1);
	// 	mockHttpOut.expectedBodiesReceived("Hello, World!");

	// 	template.sendBody("direct:scheduler:my_sch", "Hello, World!");

	// 	mockHttpOut.assertIsSatisfied();
	// }


}
