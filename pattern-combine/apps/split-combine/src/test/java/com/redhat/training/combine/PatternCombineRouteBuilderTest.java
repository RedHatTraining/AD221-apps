package com.redhat.training.combine;

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

@RunWith( CamelSpringBootRunner.class )
@SpringBootTest( properties = { "camel.springboot.java-routes-include-pattern=**/CombineRoute*" } )
@UseAdviceWith
@DirtiesContext( classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD )
public class PatternCombineRouteBuilderTest {
	private static final String SEPARATOR = System.getProperty( "line.separator" );
	private static final String MOCK_FILE_OUTPUT = "mock:file:output";
	private static final String DIRECT_INPUT = "direct:input";

	@Autowired
	private ProducerTemplate template;

	@Autowired
	private CamelContext context;

	@EndpointInject( uri = MOCK_FILE_OUTPUT )
	MockEndpoint outputFileMock;

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
	public void tenLinesOneGroup() throws Exception {
		outputFileMock.expectedMessageCount( 1 );

		template.sendBody( DIRECT_INPUT, content() );

		outputFileMock.assertIsSatisfied();
	}

	private void mockRouteEndpoints() throws Exception {
		context.getRouteDefinition( "split-combine-pipeline" )
				.adviceWith(
						context,
						new AdviceWithRouteBuilder() {
							@Override
							public void configure() {
								replaceFromWith( DIRECT_INPUT );

								interceptSendToEndpoint( "file:orders/outgoing?fileName=orders2.csv" )
										.skipSendToOriginalEndpoint()
										.to( MOCK_FILE_OUTPUT );
							}
						} );
	}

	private String content() {
		return "DPR,DPR,100,AD-982-708-895-F-6C894FB,52039657,1312378,83290718932496,04/12/2018,2,200.0,-200.0,0.0,0.0,0.0,0.0,0,2" + SEPARATOR +
		"RJF,Product P,28 / A / MTM,83-490-E49-8C8-8-3B100BC,56914686,3715657,36253792848113,01/04/2019,2,190.0,-190.0,0.0,0.0,0.0,0.0,0,2" + SEPARATOR +
		"CLH,Product B,32 / B / FtO,68-ECA-BC7-3B2-A-E73DE1B,24064862,9533448,73094559597229,05/11/2018,0,164.8,-156.56,-8.24,0.0,0.0,0.0,-2,2" + SEPARATOR +
		"NMA,Product F,40 / B / FtO,6C-1F1-226-1B3-2-3542B41,43823868,4121004,53616575668264,19/02/2019,1,119.0,-119.0,0.0,0.0,0.0,0.0,0,1" + SEPARATOR +
		"NMA,Product F,40 / B / FtO,6C-1F1-226-1B3-2-3542B41,43823868,4121004,29263220319421,19/02/2019,1,119.0,-119.0,0.0,0.0,0.0,0.0,0,1" + SEPARATOR +
		"OTH,Product F,40 / B / FtO,53-5CA-7CF-8F5-9-28CB78B,43823868,4121004,53616575668264,19/02/2019,1,119.0,-119.0,0.0,0.0,0.0,0.0,0,1" + SEPARATOR +
		"OTH,Product F,40 / B / FtO,53-5CA-7CF-8F5-9-28CB78B,43823868,4121004,29263220319421,19/02/2019,1,119.0,-119.0,0.0,0.0,0.0,0.0,0,1" + SEPARATOR +
		"NMA,Product F,40 / B / FtO,6C-1F1-226-1B3-2-3542B41,43823868,4121004,13666410519728,20/02/2019,1,119.0,-119.0,0.0,0.0,0.0,0.0,0,1" + SEPARATOR +
		"NMA,Product F,40 / B / FtO,6C-1F1-226-1B3-2-3542B41,43823868,4121004,13666410519728,20/02/2019,1,119.0,-119.0,0.0,0.0,0.0,0.0,0,1" + SEPARATOR +
		"OTH,Product F,40 / C / FtO,8B-2C5-548-6C6-E-B5EECBC,43823868,4121004,80657249973427,22/02/2019,1,119.0,-119.0,0.0,0.0,0.0,0.0,0,1";
	}
}
