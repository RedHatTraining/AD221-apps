package com.redhat.training.testkit;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.file.GenericFile;
import org.apache.camel.test.junit4.TestSupport;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.EnableRouteCoverage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = Application.class)
public class FileRouteBuilderTest {

	@Autowired
	private ProducerTemplate producerTemplate;

	@Autowired
	private ConsumerTemplate consumerTemplate;

	@Autowired
	private CamelContext context;


	@BeforeEach
	public void before() throws IOException {
		TestSupport.deleteDirectory("in");
	}

	// @Test
	// public void testCamelRoute() {
	// 	producerTemplate.sendBodyAndHeader("file:in", "file",Exchange.FILE_NAME, "testFile.txt");
	// 	GenericFile receiveBody = (GenericFile) consumerTemplate.receiveBody("file:out");
	// 	String content = receiveBody.getFileNameOnly();
	// 	assertEquals("testFile.txt", content);
	// }

	// @Test
	// public void testCamelRouteContext() throws Exception {
	// 	producerTemplate.sendBodyAndHeader("file:in", "file",Exchange.FILE_NAME, "testFile.txt");

	// 	Thread.sleep(2000);

	// 	File target = new File("out/testFile.txt");
	// 	assertTrue("File does not exist", target.exists());
	// 	String content = context.getTypeConverter().convertTo(String.class, target);
	// 	assertEquals("file", content);
	// }

	@Value("classpath:test_expections.html")
	Resource exceptionsResourceFile;

	@Value("classpath:test_warnings.html")
	Resource warningsResourceFile;

	@Test
	public void abc() throws Exception {
		String exceptionsHtml = new String(exceptionsResourceFile.getInputStream().readAllBytes());

		producerTemplate.sendBody("direct:parseErrors", exceptionsHtml);

		Thread.sleep(2000);

		File target = new File("out/exceptions.txt");

		assertTrue("File does not exist", target.exists());

		String content = context.getTypeConverter().convertTo(String.class, target);

		assertEquals("hello", content);

	}

	// @AfterEach
	// public void after() throws IOException {
	// 	TestSupport.deleteDirectory("out");
	// }

}
