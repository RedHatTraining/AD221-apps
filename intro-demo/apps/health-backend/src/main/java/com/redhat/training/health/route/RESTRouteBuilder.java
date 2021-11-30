package com.redhat.training.health.route;

import java.net.URI;

import javax.enterprise.context.ApplicationScoped;

import com.redhat.training.health.route.aggregation.CovidCountryJsonDataAggregationStrategy;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.http.client.utils.URIBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
class RESTRouteBuilder extends RouteBuilder{

    @ConfigProperty(name = "info.countries.europe.rest.endpoint") 
    String countriesEndpoint;


    @ConfigProperty(name = "info.covid.data.rest.endpoint") 
    String covidDataEndpoint;



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

        rest("/covid-data-enriched")
            .get()
            .route()
            .to("direct:enriched-data")
            .endRest();

        from("direct:enriched-data")
            .to("direct:countries-data")
            .enrich("direct:covid-data", new CovidCountryJsonDataAggregationStrategy());

        from("direct:covid-data")
            .removeHeaders("CamelHttp*")
            .setHeader(Exchange.HTTP_METHOD, constant("GET"))
            .to(covidDataEndpoint);

        from("direct:countries-data")
            .removeHeaders("CamelHttp*")
            .setHeader(Exchange.HTTP_METHOD, constant("GET"))
            .to(countriesEndpoint);
    }
    
}
