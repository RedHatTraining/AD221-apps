package com.redhat.training.messaging;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SoapRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		from("direct:start")
			.routeId("soap-route")
			.setBody(constant("2"))
            .log("New body value: ${body}")
            .bean(GetFootprintBuilder.class)
            .setHeader("operationName", constant("GetFootprint"))
            .to("cxf://http://localhost:8080/footprints.php"
            + "?serviceClass=footprintservice.FootprintService"
            + "&wsdlURL=/wsdl/Footprint.wsdl")
            .log("Body from SoapRouteBuilder: ${body}");
		}

}
