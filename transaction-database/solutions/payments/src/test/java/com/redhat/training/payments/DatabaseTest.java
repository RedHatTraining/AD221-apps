package com.redhat.training.payments;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.camel.ConsumerTemplate;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@RunWith( CamelSpringBootRunner.class )
@SpringBootTest
public class DatabaseTest {

	@Autowired
	private ConsumerTemplate consumerTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Before
	public void waitForRoute()  throws InterruptedException {
		consumerTemplate.receive("direct:payment_analysis_complete", 30000);
		Thread.sleep(2000);
	}

	@Test
	public void testAnalysisIsSetAsCompletedInDB() {
		List<Map<String, Object>> rows = jdbcTemplate.queryForList("select * from payment_analysis;");

		rows.forEach( each -> {
			String status = (String) each.get("analysis_status");
			assertEquals("Completed", status);
		});
	}


	@Test
	public void testFraudScoreIsCorrectInDB() {
		List<Map<String, Object>> rows = jdbcTemplate.queryForList("select * from payment_analysis;");

		BigDecimal score = (BigDecimal) rows.get(0).get("fraud_score");

		assertEquals(0.021, score.doubleValue());
	}

}
