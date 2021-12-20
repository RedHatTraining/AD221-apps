package com.redhat.training.health.route;


import com.redhat.training.health.service.CovidService;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class RESTRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		restConfiguration()
			.component("servlet")
			.bindingMode(RestBindingMode.json);

		rest("/cases")
			.get("/")
			.route().routeId("cases")
			.bean(CovidService.class, "getAllCovidCases")
			.endRest();

		rest("/vaccinations")
			.get("/")
			.route().routeId("vaccinations")
			.bean(CovidService.class, "getAllCovidVaccinations")
			.endRest();

	}

}
