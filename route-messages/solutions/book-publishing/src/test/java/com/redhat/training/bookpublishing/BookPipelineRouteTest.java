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

@RunWith( CamelSpringBootRunner.class )
@SpringBootTest
@UseAdviceWith
public class BookPipelineRouteTest {

	@Autowired
	private ProducerTemplate template;

	@Autowired
	private CamelContext context;

	@EndpointInject(uri = "mock:file:editor")
	MockEndpoint fileMockEditor;

	@EndpointInject(uri = "mock:file:graphic_designer")
	MockEndpoint fileMockGraphicDesigner;

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
	public void technicalBookIsDeliveredToEditorAndGraphicalDesigner() throws Exception {
		fileMockEditor.expectedMessageCount(1);
		fileMockGraphicDesigner.expectedMessageCount(1);

		template.sendBody(
			"direct:manuscripts",
			technicalContent()
		);

		fileMockEditor.assertIsSatisfied();
		fileMockGraphicDesigner.assertIsSatisfied();
	}

	@Test
	public void novelBookIsDeliveredToEditor() throws Exception {
		fileMockEditor.expectedMessageCount(1);
		fileMockGraphicDesigner.expectedMessageCount(0);

		template.sendBody(
				"direct:manuscripts",
				novelContent()
		);

		fileMockEditor.assertIsSatisfied();
		fileMockGraphicDesigner.assertIsSatisfied();
	}

	private void mockRouteEndpoints() throws Exception {
		context.getRouteDefinition( "book-pipeline" )
		    .adviceWith(
			    context,
				new AdviceWithRouteBuilder() {
					@Override
					public void configure() {
						replaceFromWith( "direct:manuscripts" );

						interceptSendToEndpoint("file://data/pipeline/editor")
						    .skipSendToOriginalEndpoint()
							.to("mock:file:editor");

						interceptSendToEndpoint("file://data/pipeline/graphic-designer")
							.skipSendToOriginalEndpoint()
							.to("mock:file:graphic_designer");
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
