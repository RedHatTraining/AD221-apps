package com.redhat.training.testkit;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileRouteBuilder extends RouteBuilder {

    public static final String ROUTE_ID = "test-kit";

    @Override
    public void configure() throws Exception {

        from("direct:parseErrors")
            .log("${body}")
            .choice()
                .when().xpath("//div[@id='warnings']")
                    .to("language:xpath://body/div/ul/li/text()")
                    .to("file:out?fileName=warnings.txt")
                .when().xpath("//div[@id='exceptions']")
                    .to("language:xpath://body/div/article/text()")
                    .to("file:out?fileName=exceptions.txt");

    }
}