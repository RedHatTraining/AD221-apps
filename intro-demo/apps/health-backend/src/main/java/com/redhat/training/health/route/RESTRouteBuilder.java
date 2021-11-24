package com.redhat.training.health.route;

import java.net.URI;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.http.client.utils.URIBuilder;

public class RESTRouteBuilder extends RouteBuilder{

    @Override
    public void configure() throws Exception {
        restConfiguration()
            .bindingMode(RestBindingMode.json);

            URI dataUrl = new URIBuilder()
            .setScheme("mongodb")
            .setHost("camelMongoClient")
            .addParameter("database", "covid-db")
            .addParameter("collection", "covid-data")
            .addParameter("operation", "findAll")
            .build();

        rest("/covid-data")
            .get()
            .route()
            .to(dataUrl.toString())
            .endRest();
    }
    
}
