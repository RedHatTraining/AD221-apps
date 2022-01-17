package com.redhat.training.payslipvalidator.route;

import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.UseAdviceWith;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@UseAdviceWith
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PriceProcessRouteTest extends PayslipTests {

    @Test
    public void routeSendsMessageToCorrectEndpoint () throws Exception {
        fileMockValidationOk.expectedMessageCount(1);

        template.sendBody(
                "direct:payslips-price",
                validContent()
        );

        fileMockValidationOk.assertIsSatisfied();
    }

    @Test
    public void numberFormatExceptionCapturedByOnExceptionClause () throws Exception {
        fileMockErrorPrice.expectedMessageCount(2);

        template.sendBody(
                "direct:payslips-price",
                amountErrorContent()
        );

        template.sendBody(
                "direct:payslips-price",
                priceErrorContent()
        );

        fileMockErrorPrice.assertIsSatisfied();
    }


    @Test
    public void wrongCalculationExceptionCapturedByDeadLetter () throws Exception {
        fileMockErrorDeadLetter.expectedMessageCount(1);

        template.sendBody(
                "direct:payslips-price",
                wrongCalculationErrorContent()
        );

        fileMockErrorDeadLetter.assertIsSatisfied();
    }
}
