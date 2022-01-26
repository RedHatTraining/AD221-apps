package com.redhat.training;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;


/**
 * A spring-boot application that includes a Camel route builder to setup the Camel routes
 */
@Component
public class TemperaturesRESTRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        // - REST config
        restConfiguration()
			.component("servlet")
			.bindingMode(RestBindingMode.json);


        // - Error handling
        onException(HttpHostConnectException.class)
            .to("direct:onException");

        onException(ConnectTimeoutException.class)
            .to("direct:onException");

        from("direct:onException")
            .routeId("processException")
            .process(exchange -> {
                exchange
                    .getIn()
                    .setHeader("error", exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class));
            })
            // TODO: use the route-health bean to set health down
            // .bean("[BEAN_NAME]", "[METHOD_NAME]")
            ;


        // - REST Routes
        rest("/")
            .get()
            .route()
            .transform()
            .constant("Welcome. Visit '/temperatures/fahrenheit' to run the route");

        rest("/temperatures/fahrenheit")
            .get()
            .to("direct:celsiusToFahrenheit");

        from("direct:celsiusToFahrenheit")
            .routeId("celsiusToFahrenheit")
            .to("http4://{{temperature.route.celsius-service}}/temperatures?bridgeEndpoint=true&connectTimeout=5000")
            .unmarshal()
            .json(JsonLibrary.Jackson)
            .bean(TemperaturesConverter.class, "valuesToFahrenheit")
            // TODO: use the Wire Tap EIP with the route-health bean to set health up
            // .wireTap("bean:[BEAN_NAME]?method=[METHOD_NAME]")
            ;
    }
}
