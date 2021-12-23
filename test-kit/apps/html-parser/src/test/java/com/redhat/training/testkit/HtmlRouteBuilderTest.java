package com.redhat.training.testkit;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.junit4.TestSupport;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;


// TODO: add annotations
public class HtmlRouteBuilderTest {

	@Autowired
	private ProducerTemplate producerTemplate;

	@Autowired
	private ConsumerTemplate consumerTemplate;

	@Value( "classpath:test_errors.html" )
	Resource errors;

	@Value( "classpath:test_warnings.html" )
	Resource warnings;

	@AfterEach
	public void after() throws IOException {
		TestSupport.deleteDirectory( "out" );
	}

	@Test
	public void testRouteWritesLatestWarningToFile() throws Exception {
		String warningsHtml = new String( warnings.getInputStream().readAllBytes() );

		producerTemplate.sendBody( "direct:parseHtmlErrors", warningsHtml );

		File latestWarningFile = new File( "out/latest-warning.txt" );
		assertTrue( "out/latest-warning.txt does not exist", latestWarningFile.exists() );
	}

	@Test
	public void testRouteWritesLatestErrorToFile() throws Exception {
		String errorsHtml = new String( errors.getInputStream().readAllBytes() );

		producerTemplate.sendBody( "direct:parseHtmlErrors", errorsHtml );

		File latestWarningFile = new File( "out/latest-error.txt" );
		assertTrue( "out/latest-error.txt does not exist", latestWarningFile.exists() );
	}

	@Test
	public void testRouteParsesLatestWarningText() throws Exception {
		String warningsHtml = new String( warnings.getInputStream().readAllBytes() );

		producerTemplate.sendBody( "direct:parseHtmlErrors", warningsHtml );

		Thread.sleep(2);

		String body = consumerTemplate.receiveBody( "file:out", String.class );
		assertTrue( body.contains( "DeprecationWarning: Creating a LegacyVersion has been deprecated" ) );
	}


	@Test
	public void testRouteParsesLatestErrorText() throws Exception {
		// TODO: read errors file

		// TODO: send errorsHtml as the body to direct:parseHtmlErrors

		Thread.sleep(2);

		// TODO: receive the body from file:out

		// TODO: assert body contains a portion of the first article
		assertTrue(false);
	}

}
