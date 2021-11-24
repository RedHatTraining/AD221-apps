package com.redhat.training.health.route;

import java.net.URI;

import org.apache.camel.builder.RouteBuilder;
import org.apache.http.client.utils.URIBuilder;

public class CovidDataRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        URI dataUrl = new URIBuilder()
            .setScheme("mongodb")
            .setHost("camelMongoClient")
            .addParameter("database", "covid-db")
            .addParameter("collection", "covid-data")
            .addParameter("operation", "insert")
            .build();
            
        from("kafka:covid-data")
            .to(dataUrl.toString());
    }
}

