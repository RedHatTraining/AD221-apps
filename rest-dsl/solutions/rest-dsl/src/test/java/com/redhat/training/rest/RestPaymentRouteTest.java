package com.redhat.training.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest( webEnvironment = WebEnvironment.RANDOM_PORT )
@RunWith( CamelSpringBootRunner.class )
public class RestPaymentRouteTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	@DirtiesContext
	public void testPayments() throws Exception {
		ResponseEntity<Payment[]> response = restTemplate.getForEntity( "/camel/payments", Payment[].class );
		
		assertEquals( HttpStatus.OK, response.getStatusCode() );
		assertEquals( 4, response.getBody().length );
	}

	@Test
	@DirtiesContext
	public void testPayment() throws Exception {
		ResponseEntity<Payment[]> response = restTemplate.getForEntity( "/camel/payments/12", Payment[].class );
		
		assertEquals( HttpStatus.OK, response.getStatusCode() );
		assertEquals( 1, response.getBody().length );
		assertEquals( 8474, response.getBody()[0].getOrderId() );
	}
}