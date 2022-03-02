package com.redhat.training.messaging;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import org.apache.camel.Exchange;
import org.apache.camel.component.file.FileComponent;


@Component
public class WSDLRetrievalRouteBuilder extends RouteBuilder {

	public static final String ROUTE_NAME = "get-wsdl";

	@Override
	public void configure() throws Exception {

		from("direct:get-wsdl")
			.routeId(ROUTE_NAME)
			.setHeader(Exchange.HTTP_QUERY, constant("wsdl"))
			.to("http4://localhost:8080/footprints.php")
			.setHeader("CamelFileName", constant("Footprint.wsdl"))
			.to("file:src/main/resources/wsdl")
			.to("mock:test_execution_target");
	}

}
