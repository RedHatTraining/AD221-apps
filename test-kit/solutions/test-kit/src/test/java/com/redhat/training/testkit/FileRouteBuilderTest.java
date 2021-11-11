package com.redhat.training.testkit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

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
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = Application.class)
@EnableRouteCoverage
public class FileRouteBuilderTest {

	@Autowired 
	private ProducerTemplate producerTemplate;

	@Autowired 
	private ConsumerTemplate consumerTemplate;


	@Before
	public void before() throws IOException {
		TestSupport.deleteDirectory("in");
	}

	@Test
	public void testCamelRoute() {
		producerTemplate.sendBodyAndHeader("file:in", "file",Exchange.FILE_NAME, "testFile.txt");
		GenericFile receiveBody = (GenericFile) consumerTemplate.receiveBody("file:out");
		String content = receiveBody.getFileNameOnly();
		assertEquals("testFile.txt", content);
	}

	@After
	public void after() throws IOException {
		TestSupport.deleteDirectory("out");
	}

}
