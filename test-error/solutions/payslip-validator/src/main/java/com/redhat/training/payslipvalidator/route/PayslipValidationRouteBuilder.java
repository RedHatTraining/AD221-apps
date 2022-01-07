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
        onException(NumberFormatException.class)
            .to("file://data/validation/error-price")
            .handled(true);

        // TODO: Handle errors with the dead letter channel EIP
        errorHandler(deadLetterChannel("file://data/validation/error-dead-letter")
            .disableRedelivery());

        // TODO: Add  doTry/Catch block
        from("file://data/payslips?noop=true")
            .routeId("amount-process")
            .doTry()
                .process(new AmountProcessor())
                .to("direct:process")
            .doCatch(NumberFormatException.class)
                .to("file://data/validation/error-amount")
            .endDoTry();

        from("direct:process")
            .routeId("price-process")
            .process(new PriceProcessor())
        .to("file://data/validation/correct");
    }
}
