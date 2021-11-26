package com.redhat.training.processingorders;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithBuilder;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.GenericFile;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.TestSupport;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.apache.camel.test.spring.DisableJmx;
import org.apache.camel.test.spring.EnableRouteCoverage;
import org.apache.camel.test.spring.MockEndpoints;
import org.apache.camel.test.spring.UseAdviceWith;
import org.apache.camel.util.ExchangeHelper;

@RunWith( CamelSpringBootRunner.class )
@SpringBootTest
// @DirtiesContext( classMode = ClassMode.AFTER_EACH_TEST_METHOD )
@UseAdviceWith
// @DisableJmx
public class RouteTest {

	@Autowired
	private ProducerTemplate template;

	@Autowired
	private CamelContext context;

	@EndpointInject( uri = "mock:file:customer_requests" )
	MockEndpoint fileMock;

	@Test
	public void testCamelRoute() throws Exception {

		context.getRouteDefinition( "ftpRoute" )
			.adviceWith( context, new AdviceWithRouteBuilder() {
				@Override
				public void configure() {
					// Do not use the real FTP endpoint
					replaceFromWith( "direct:ftp" );

					// Do not write to a real file
					interceptSendToEndpoint("file:.*customer_requests.*")
						.skipSendToOriginalEndpoint()
						.to("mock:file:customer_requests");
				}
			} );

		fileMock.message(0).body().isEqualTo("Hello World");

		context.start();

		template.sendBody( "direct:ftp", "Hello World" );

		fileMock.assertIsSatisfied();

		context.stop();

	}

}
