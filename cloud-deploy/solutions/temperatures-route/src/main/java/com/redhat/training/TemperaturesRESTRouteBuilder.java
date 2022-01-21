package com.redhat.training;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.http.conn.HttpHostConnectException;


/**
 * A spring-boot application that includes a Camel route builder to setup the Camel routes
 */
@Component
public class TemperaturesRESTRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration()
			.component("servlet")
			.bindingMode(RestBindingMode.json);

        onException(HttpHostConnectException.class)
            .process(exchange -> {
                exchange
                    .getIn()
                    .setHeader("error", exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class));
            })
            // TODO: use the route-health bean to set health status to down
            .bean("route-health", "down");

        rest("/")
            .get()
            .route()
            .transform()
            .constant("Welcome. Visit '/temperatures/fahrenheit' to run the route");

        rest("/temperatures/fahrenheit")
            .get()
            .to("direct:celsiusToFahrenheit");

        from("direct:celsiusToFahrenheit")
            .to("http4://{{temperature.route.celsius-service}}/temperatures?bridgeEndpoint=true")
            .unmarshal()
            .json(JsonLibrary.Jackson)
            .bean(TemperaturesConverter.class, "valuesToFahrenheit")
            // TODO: use the route-health bean to set health status to up
            .wireTap("bean:route-health?method=up");
    }
}
