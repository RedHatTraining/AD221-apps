package com.redhat.training.processingorders;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.UseAdviceWith;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith( CamelSpringBootRunner.class )
@SpringBootTest
@UseAdviceWith
public class FtpToFileRouteBuilderTest {

	@Autowired
	private ProducerTemplate template;

	@Autowired
	private CamelContext context;

	@EndpointInject( uri = "mock:file:customer_requests" )
	MockEndpoint fileMock;

	@Before
	public void setUp() throws Exception {
		mockRouteEndpoints();
		context.start();
	}

	@After
	public void tearDown() throws Exception {
		context.stop();
	}

	@Test
	public void testFTPFileContentIsWrittenToFile() throws Exception {
		fileMock.message( 0 ).body().isEqualTo( "Hello World" );

		template.sendBody( "direct:ftp", "Hello World" );

		fileMock.assertIsSatisfied();
	}

	private void mockRouteEndpoints() throws Exception {
		context.getRouteDefinition( "ftpRoute" )
				.adviceWith( context, new AdviceWithRouteBuilder() {
					@Override
					public void configure() {
						// Do not use the real FTP endpoint
						replaceFromWith( "direct:ftp" );

						// Do not write to a real file
						interceptSendToEndpoint( "file:.*customer_requests.*" )
								.skipSendToOriginalEndpoint()
								.to( "mock:file:customer_requests" );
					}
				} );
	}

}
