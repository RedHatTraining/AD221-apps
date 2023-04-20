package com.redhat.training.health.route;

import javax.enterprise.context.ApplicationScoped;

import com.redhat.training.health.route.aggregation.CovidCountryJsonDataAggregationStrategy;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
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

        rest("/covid-data")
            .get()
            .route()
            .to("mongodb:camelMongoClient?database=covid-db&collection=covid-data&operation=findAll")
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
