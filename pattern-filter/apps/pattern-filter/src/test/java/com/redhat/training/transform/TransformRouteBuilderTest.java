package com.redhat.training.transform;

import static com.redhat.training.transform.Application.OrderProducer;
import com.redhat.training.model.Order;
import com.redhat.training.model.Customer;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.EndpointInject;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.lang.Integer;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = {Application.class},
    properties = { "camel.springboot.java-routes-include-pattern=**/Transform*"})
public class TransformRouteBuilderTest {

    @Autowired
    private ProducerTemplate producerTemplate;

    @EndpointInject(uri = "mock:fufillmentSystem")
    private MockEndpoint mock;

    @Test
    public void testLogOrderRoute() throws Exception {
        // Sets an assertion

        String exectedJson = "{\"orderItems\":[{\"extPrice\":\"10\","
            + "\"id\":\"1\",\"item\":{\"author\":\"ci1 author\","
            + "\"category\":\"category ci1\","
            + "\"description\":\"catalog item 1 description\","
            + "\"imagePath\":\"path/to/ci1.png\",\"newItem\":\"true\","
            + "\"price\":\"5\",\"sku\":\"ci1\",\"title\":\"catalog item 1\"},"
            + "\"quantity\":\"2\"},{\"extPrice\":\"30\",\"id\":\"2\","
            + "\"item\":{\"author\":\"ci2 author\",\"category\":\"category ci2\","
            + "\"description\":\"catalog item 2 description\","
            + "\"imagePath\":\"path/to/c21.png\",\"newItem\":\"true\","
            + "\"price\":\"10\",\"sku\":\"ci2\",\"title\":\"catalog item 2\"},"
            + "\"quantity\":\"3\"}],\"customer\":{\"admin\":\"false\","
            + "\"email\":\"tanderson@email.com\",\"firstName\":\"Tony\","
            + "\"lastName\":\"Anderson\",\"password\":\"password\","
            + "\"username\":\"tanderson\"},\"delivered\":\"false\","
            + "\"discount\":\"0.012\",\"id\":\"5\"}";

        mock.expectedBodiesReceived(exectedJson);

        // Builds sample test data
        OrderProducer orderProducer  = new OrderProducer(
			new Integer(5),
			new BigDecimal("0.012"),
			false,
			new Customer(
				"Tony",
				"Anderson",
				"tanderson",
				"password",
				"tanderson@email.com",
				false
			)
        );

        Order testOrder = orderProducer.getOrder();

        // Sends a message to the start component
        producerTemplate.sendBody("jms:queue:orderInput", testOrder);

        // Verifies that the mock component received 1 message
        mock.assertIsSatisfied();
    }

}
