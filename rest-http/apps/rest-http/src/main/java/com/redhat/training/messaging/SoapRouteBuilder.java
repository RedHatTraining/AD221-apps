package com.redhat.training.messaging;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
//import org.apache.camel.component.cxf.common.message.CxfConstants;

@Component
public class SoapRouteBuilder extends RouteBuilder {

    public static final String ROUTE_NAME = "soap-route";

    @Override
    public void configure() throws Exception {

        from("direct:soap")
            .routeId(ROUTE_NAME)
            .setBody(jsonpath("$.Name"))
            .log("New body value: ${body}")
            //.bean(<Request object builder class>)
            //.setHeader(CxfConstants.OPERATION_NAME, constant("<operation name>"))
            //.setHeader(CxfConstants.OPERATION_NAMESPACE, constant("<operation namespace>"))
            //.to("cxf://<soap service URI>"
            //+ "?serviceClass=<soap service interface>")
            .log("From SoapRouteBuilder: ${body[0].carbonFootprint}")
            .to("direct:log_orders");
    }

}
