package com.redhat.training.health.route;

import org.apache.camel.builder.RouteBuilder;

public class CovidDataRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("kafka:covid-data")
            .to("mongodb:camelMongoClient?database=covid-db&collection=covid-data&operation=insert")
        .routeId("kafka-to-mongo");
    }
}

