package com.redhat.training.processingorders;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith( CamelSpringBootRunner.class )
@SpringBootTest(properties = {
	"routea.destination = mock:out"
})
public class PropertiesTest {

	@Autowired
	private ProducerTemplate template;

	@EndpointInject( uri = "mock:out" )
	MockEndpoint mockTest2;


	@Test
	public void testUseProperties() throws InterruptedException {
		mockTest2.expectedMessageCount(1);
		mockTest2.expectedBodiesReceived("Hola");

		template.sendBody("direct:a", "Hola");

		mockTest2.assertIsSatisfied();
	}


}
