package com.redhat.training.environment.route;

import com.redhat.training.carbonfootprintservice.CarbonFootprintRequest;
import com.redhat.training.carbonfootprintservice.CarbonFootprintResponse;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.apache.cxf.message.MessageContentsList;
import org.springframework.stereotype.Component;

@Component
public class SoapRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer:foo?period=5000")
            .routeId("soap-consumer-route")
            .process(new Processor() { // WE CAN EXTRACT THIS PROCESSOR
                @Override
                public void process(Exchange exchange) throws Exception {
                        CarbonFootprintRequest soapRequest = new CarbonFootprintRequest();
                        soapRequest.setID("customer-a");

                    exchange.getIn().setBody(soapRequest);
                }
            })
            .setHeader(CxfConstants.OPERATION_NAME, constant("{{soap.endpoint.operation}}"))
            .setHeader(CxfConstants.OPERATION_NAMESPACE, constant("{{soap.endpoint.namespace}}"))
            .to("cxf:bean:cxfGetCarbonFootprint")
            .process(new Processor() { // WE CAN EXTRACT THIS PROCESSOR
                @Override
                public void process(Exchange exchange) throws Exception {
                    MessageContentsList response = (MessageContentsList) exchange.getIn().getBody();
                    CarbonFootprintResponse r = (CarbonFootprintResponse) response.get(0);
                    exchange.getIn().setBody("Carbon Footprint: " + r.getCarbonFootprint());
                }
            })
            .log("${body}");
    }
}


