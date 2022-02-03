package com.redhat.training.emergency;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
public class EmergencyLocationRouteBuilderTest {

	private final Logger LOGGER = LoggerFactory.getLogger(EmergencyLocationRouteBuilderTest.class);

	@Autowired
	private ConsumerTemplate consumerTemplate;

	@Autowired
	private CamelContext context;

	@Autowired
	private JdbcTemplate jdbcTemplate;


	@Test
	public void testEmergencyLocationRoute() throws Exception {

		RouteDefinition locationRouteDef = context.getRouteDefinition("emergency-location-route");

		configureRoute(locationRouteDef);

		context.startRoute(locationRouteDef.getId());

		assertErrorNotOccured();

		context.stopRoute(locationRouteDef.getId());
	}

	@Test
	public void testKafkaConsumerRoute() throws Exception {

		RouteDefinition kafkaRouteDef = context.getRouteDefinition("kafka-consumer-route");

		configureRoute(kafkaRouteDef);

		context.startRoute(kafkaRouteDef.getId());

		assertErrorNotOccured();

		context.stopRoute(kafkaRouteDef.getId());

		assertDBHasRecords();
	}

	private void configureRoute(RouteDefinition routeDef) throws Exception{
		routeDef.adviceWith(context, new AdviceWithRouteBuilder() {
				@Override
				public void configure() {
					interceptSendToEndpoint("direct:logger")
					.skipSendToOriginalEndpoint()
					.to("direct:output");
				}
			}
		);
	}

	private void assertErrorNotOccured() throws Exception{
		String body = consumerTemplate.receive("direct:output").getIn().getBody(String.class);
		assertNotEquals("errorOccured", body); 
	}

	private void assertDBHasRecords(){
		Integer recordCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM locations", Integer.class);
		LOGGER.info("The locations table has " + recordCount + " records");
		assertTrue(recordCount >= 49);
	}

}
