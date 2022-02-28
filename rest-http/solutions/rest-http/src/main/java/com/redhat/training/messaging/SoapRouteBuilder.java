package com.redhat.training.messaging;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import org.apache.camel.component.cxf.common.message.CxfConstants;

@Component
public class SoapRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		from("direct:start")
			.routeId("soap-route")
			.setBody(constant("customer-a"))
            .log("New body value: ${body}")
            .bean(GetFootprintBuilder.class)
            .setHeader(CxfConstants.OPERATION_NAME, constant("CarbonFootprint"))
            .setHeader(CxfConstants.OPERATION_NAMESPACE, constant("http://training.redhat.com/CarbonFootprintService/"))
            .to("cxf://http://localhost:8080/footprints.php"
            + "?serviceClass=com.redhat.training.carbonfootprintservice.CarbonFootprintEndpoint")
            .log("From SoapRouteBuilder: ${body[0].carbonFootprint}");
		}

}
