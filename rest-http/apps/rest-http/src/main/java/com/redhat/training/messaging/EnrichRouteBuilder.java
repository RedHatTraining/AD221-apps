package com.redhat.training.messaging;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class EnrichRouteBuilder extends RouteBuilder {

	public static final String ROUTE_NAME = "enrich-route";

	@Override
	public void configure() throws Exception {

		from("direct:enrich")
			.routeId(ROUTE_NAME)
			//.enrich("<enrichment source>", <aggregation strategy>)
			.log("Order sent to fulfillment: ${body}")
			.log("New Header value: ${in.header.FOOT_PRINT}")
			.to("mock:fulfillmentSystem");
	}
}
