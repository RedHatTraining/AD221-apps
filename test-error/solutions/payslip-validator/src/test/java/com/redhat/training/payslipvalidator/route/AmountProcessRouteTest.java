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
public class AmountProcessRouteTest extends PayslipTests {

    @Test
    public void routeSendsMessageToProcessEndpoint () throws Exception {
        mockProcess.expectedMessageCount(1);
        fileMockErrorAmount.expectedMessageCount(0);

        template.sendBody(
                "direct:payslips-amount",
                validContent()
        );

        mockProcess.assertIsSatisfied();
        fileMockErrorAmount.assertIsSatisfied();
    }

    @Test
    public void routeCatchesNumberFormatException () throws Exception {
        mockProcess.expectedMessageCount(0);
        fileMockErrorAmount.expectedMessageCount(1);

        template.sendBody(
                "direct:payslips-amount",
                amountErrorContent()
        );

        mockProcess.assertIsSatisfied();
        fileMockErrorAmount.assertIsSatisfied();
    }
}
