package com.redhat.training.payslipvalidator.route;

import com.redhat.training.payslipvalidator.processor.AmountProcessor;
import com.redhat.training.payslipvalidator.processor.PriceProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class PayslipValidationRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        // TODO: Handle errors with the onException clause

        // TODO: Handle errors with the dead letter channel EIP

        // TODO: Add  doTry/Catch block
        from("file://data/payslips?noop=true")
            .routeId("amount-process")
            .process(new AmountProcessor())
        .to("direct:process");

        from("direct:process")
            .routeId("price-process")
            .process(new PriceProcessor())
        .to("file://data/validation/correct");
    }
}
