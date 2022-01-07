package com.redhat.training.payslipvalidator.route;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

abstract public class PayslipTests {

    @Autowired
    protected ProducerTemplate template;

    @Autowired
    protected CamelContext context;

    @EndpointInject(uri = "mock:process")
    MockEndpoint mockProcess;

    @EndpointInject(uri = "mock:file:error_amount")
    MockEndpoint fileMockErrorAmount;

    @EndpointInject(uri = "mock:file:error_dead_letter")
    MockEndpoint fileMockErrorDeadLetter;

    @EndpointInject(uri = "mock:file:error_price")
    MockEndpoint fileMockErrorPrice;

    @EndpointInject(uri = "mock:file:validation_ok")
    MockEndpoint fileMockValidationOk;

    @Before
    public void setUp() throws Exception {
        mockRouteEndpoints();
        context.start();
    }

    @After
    public void tearDown() throws Exception {
        context.stop();
    }

    protected void mockRouteEndpoints() throws Exception {
        mockAmountProcessRoute();
        mockPriceProcessRoute();
    }

    protected void mockAmountProcessRoute() throws Exception {
        context.getRouteDefinition("amount-process")
                .adviceWith(
                        context,
                        new AdviceWithRouteBuilder() {
                            @Override
                            public void configure() {
                                replaceFromWith("direct:payslips-amount");

                                interceptSendToEndpoint("direct:process")
                                        .skipSendToOriginalEndpoint()
                                        .to("mock:process");

                                interceptSendToEndpoint("file://data/validation/error-amount")
                                        .skipSendToOriginalEndpoint()
                                        .to("mock:file:error_amount");

                                // Dead Letter
                                interceptSendToEndpoint("file://data/validation/error-dead-letter")
                                        .skipSendToOriginalEndpoint()
                                        .to("mock:file:error_dead_letter");

                                // onException Clause
                                interceptSendToEndpoint("file://data/validation/error-price")
                                        .skipSendToOriginalEndpoint()
                                        .to("mock:file:error_price");
                            }
                        }
                );
    }

    protected void mockPriceProcessRoute() throws Exception {
        context.getRouteDefinition("price-process")
                .adviceWith(
                        context,
                        new AdviceWithRouteBuilder() {
                            @Override
                            public void configure() {
                                replaceFromWith("direct:payslips-price");

                                interceptSendToEndpoint("file://data/validation/correct")
                                        .skipSendToOriginalEndpoint()
                                        .to("mock:file:validation_ok");
                            }
                        }
                );
    }

    protected String validContent() {
        return "<payslip><totalPayslip>6000.00</totalPayslip><payslipItems><payslipItem><payslipItemQty>30</payslipItemQty><payslipItemPrice>200.00</payslipItemPrice></payslipItem></payslipItems></payslip>";
    }

    protected String amountErrorContent() {
        return "<payslip><payslipItems><payslipItem><payslipItemQty>1.5</payslipItemQty><payslipItemPrice>1024.20</payslipItemPrice></payslipItem></payslipItems></payslip>";
    }

    protected String priceErrorContent() {
        return "<payslip><payslipItems><payslipItem><payslipItemId>456</payslipItemId><payslipItemQty>1</payslipItemQty><description>Award</description><payslipItemPrice>NA</payslipItemPrice></payslipItem></payslipItems></payslip>";
    }

    protected String wrongCalculationErrorContent() {
        return "<payslip><totalPayslip>1.23</totalPayslip><payslipItems><payslipItem><payslipItemId>456</payslipItemId><payslipItemQty>1</payslipItemQty><description>Award</description><payslipItemPrice>2.50</payslipItemPrice></payslipItem></payslipItems></payslip>";
    }
}
