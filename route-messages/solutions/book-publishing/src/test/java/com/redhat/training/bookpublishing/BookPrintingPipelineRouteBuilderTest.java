package com.redhat.training.bookpublishing;

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
import org.springframework.test.annotation.DirtiesContext;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(
		properties = { "camel.springboot.java-routes-include-pattern=**/BookPrinting*"}
)
@UseAdviceWith
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookPrintingPipelineRouteBuilderTest {

	@Autowired
	private ProducerTemplate template;

	@Autowired
	private CamelContext context;

	@EndpointInject(uri = "mock:file:technical")
	MockEndpoint fileMockTechnical;

	@EndpointInject(uri = "mock:file:novel")
	MockEndpoint fileMockNovel;

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
	public void technicalBookIsDeliveredToTechnicalDirectory() throws Exception {
		fileMockTechnical.expectedMessageCount(1);
		fileMockNovel.expectedMessageCount(0);

		template.sendBody(
			"direct:ready-for-printing",
			technicalContent()
		);

		fileMockTechnical.assertIsSatisfied();
		fileMockNovel.assertIsSatisfied();
	}

	@Test
	public void novelBookIsDeliveredToNovelDirectory() throws Exception {
		fileMockTechnical.expectedMessageCount(0);
		fileMockNovel.expectedMessageCount(1);

		template.sendBody(
				"direct:ready-for-printing",
				novelContent()
		);

		fileMockTechnical.assertIsSatisfied();
		fileMockNovel.assertIsSatisfied();
	}

	private void mockRouteEndpoints() throws Exception {
		context.getRouteDefinition("book-printing-pipeline")
		    .adviceWith(
			    context,
				new AdviceWithRouteBuilder() {
					@Override
					public void configure() {
						replaceFromWith("direct:ready-for-printing");

						interceptSendToEndpoint("file://data/printing-services/technical")
						    .skipSendToOriginalEndpoint()
							.to("mock:file:technical");

						interceptSendToEndpoint("file://data/printing-services/novel")
							.skipSendToOriginalEndpoint()
							.to("mock:file:novel");
					}
				}
			);
	}

	private String technicalContent() {
		return "<book><bookinfo><productname>technical</productname></bookinfo></book>";
	}

	private String novelContent() {
		return "<book><bookinfo><productname>novel</productname></bookinfo></book>";
	}
}
