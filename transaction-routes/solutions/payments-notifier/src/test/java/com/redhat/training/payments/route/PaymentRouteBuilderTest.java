package com.redhat.training.payments.route;

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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@UseAdviceWith
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PaymentRouteBuilderTest {

    @Autowired
    private ProducerTemplate template;

    @Autowired
    private CamelContext context;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @EndpointInject(uri = "mock:jms:error_dead_letter")
    MockEndpoint jmsMockErrorDeadLetter;

    @EndpointInject(uri = "mock:jms:queue")
    MockEndpoint jmsMockQueue;


    @Before
    public void beforeSetUp() throws Exception {
        mockRouteEndpoints();
        context.start();
    }

    @After
    public void tearDown() throws Exception {
        context.stop();
    }

    @Test
    public void routeSendsMessageToCorrectEndpoints () throws Exception {
        jmsMockQueue.expectedMessageCount(1);
        jmsMockErrorDeadLetter.expectedMessageCount(0);

        // Happy path
        template.sendBody(
                "direct:payments",
                validContent()
        );

        // Assert messages in the database
        assertEquals(Integer.valueOf(1), totalRecordsInDatabase());

        // Assert messages in the queue
        jmsMockQueue.assertIsSatisfied();

        // Assert messages in the dead letter queue
        jmsMockErrorDeadLetter.assertIsSatisfied();
    }

    @Test
    public void illegalStateExceptionCapturedByDeadLetter () throws Exception {
        jmsMockQueue.expectedMessageCount(0);
        jmsMockErrorDeadLetter.expectedMessageCount(1);

        // Throws IllegalStateException
        template.sendBody(
                "direct:payments",
                invalidOrderId()
        );

        // Assert ZERO messages in the database because of the rollback
        assertEquals(Integer.valueOf(0), totalRecordsInDatabase());

        // Assert ZERO messages in the queue
        jmsMockQueue.assertIsSatisfied();

        // Assert ONE message in the dead letter queue
        jmsMockErrorDeadLetter.assertIsSatisfied();
    }

    @Test
    public void invalidEmailExceptionCapturedByDeadLetter () throws Exception {
        jmsMockQueue.expectedMessageCount(0);
        jmsMockErrorDeadLetter.expectedMessageCount(1);

        // Throws InvalidEmailException
        template.sendBody(
                "direct:payments",
                invalidEmail()
        );

        // Assert ZERO messages in the database because of the rollback
        assertEquals(Integer.valueOf(0), totalRecordsInDatabase());

        // Assert ZERO messages in the queue
        jmsMockQueue.assertIsSatisfied();

        // Assert ONE message in the dead letter queue
        jmsMockErrorDeadLetter.assertIsSatisfied();
    }

    private void mockRouteEndpoints() throws Exception {
        context.getRouteDefinition("payments-process")
                .adviceWith(
                        context,
                        new AdviceWithRouteBuilder() {
                            @Override
                            public void configure() {
                                replaceFromWith("direct:payments");

                                // Notifications queue
                                interceptSendToEndpoint("jms:queue:payment-notifications")
                                        .skipSendToOriginalEndpoint()
                                        .to("mock:jms:queue");

                                // Dead Letter
                                interceptSendToEndpoint("jms:queue:dead-letter")
                                        .skipSendToOriginalEndpoint()
                                        .to("mock:jms:error_dead_letter");
                            }
                        }
                );
    }

    private Integer totalRecordsInDatabase() {
        return jdbcTemplate.queryForObject("select count(*) from payments", Integer.class);
    }

    private String validContent() {
        return "<payment><user_id>1</user_id><order_id>100</order_id><amount>100.00</amount><currency>EUR</currency><email>user@example.com</email></payment>";
    }

    private String invalidOrderId () {
        return "<payment><user_id>3</user_id><order_id>-1</order_id><amount>300.00</amount><currency>EUR</currency><email>fail@example.com</email></payment>";
    }

    private String invalidEmail() {
        return "<payment><user_id>2</user_id><order_id>200</order_id><amount>200.00</amount><currency>EUR</currency><email></email></payment>";
    }
}
