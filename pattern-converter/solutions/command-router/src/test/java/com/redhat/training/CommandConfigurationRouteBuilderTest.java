package com.redhat.training;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
public class CommandConfigurationRouteBuilderTest {

	@Autowired
	private ConsumerTemplate consumerTemplate;

	@Autowired
	private CamelContext context;


	@Test
	public void testRoute() throws Exception {

		RouteDefinition airPurifierConfigRouteDef = context.getRouteDefinition("air-purifier-configuration-route");

		airPurifierConfigRouteDef.adviceWith(context, new AdviceWithRouteBuilder() {
				@Override
				public void configure() {
					interceptSendToEndpoint("direct:logReturnCode")
					.skipSendToOriginalEndpoint()
					.to("direct:output");
				}
			}
		);

		context.startRoute(airPurifierConfigRouteDef.getId());

		Assert.assertEquals(200, consumerTemplate.receive("direct:output").getIn().getBody());

		context.stopRoute(airPurifierConfigRouteDef.getId());
	}

}
