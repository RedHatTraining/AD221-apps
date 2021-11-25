package com.redhat.training.processingorders;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.apache.camel.Exchange;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;


// @SpringBootTest
class RouteTest extends CamelTestSupport {

	@Override
	protected RoutesBuilder createRouteBuilder() throws Exception {
		return new FileRouteBuilder();
	}

	@Test
	void testDummy() throws Exception {
		template.sendBody("ftp://services.lab.example.com", "<order></order>");

		Thread.sleep(2000);

		File target = new File("target/outbox/hello.txt");
		assertTrue("File not moved", target.exists());


	}

}
