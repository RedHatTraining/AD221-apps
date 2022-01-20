package com.redhat.training;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.springframework.stereotype.Component;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.http.conn.HttpHostConnectException;


/**
 * A spring-boot application that includes a Camel route builder to setup the Camel routes
 */
@Component
public class TemperaturesRESTRouteBuilder extends RouteBuilder {

    // @Autowired
    // RoutesHealthCheck routeChecker;

    @Override
    public void configure() throws Exception {

        restConfiguration()
			.component("servlet")
			.bindingMode(RestBindingMode.json);

        // TODO
        onException(HttpHostConnectException.class)
            .process(exchange -> {
                exchange.getIn().setHeader("error", exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class));
            })
            .bean("route-health-check", "down");

        rest("/temperatures/farenheit")
            .get().to("direct:celsiusToFahrenheit");

        from("direct:celsiusToFahrenheit")
            .to("http4://{{temperature.route.celsius-service}}/temperatures?bridgeEndpoint=true")
            .unmarshal()
            .json(JsonLibrary.Jackson)
            .bean(TemperaturesConverter.class, "valuesToFahrenheit")
            // TODO
            .wireTap("bean:route-health-check?method=up");
    }
}
