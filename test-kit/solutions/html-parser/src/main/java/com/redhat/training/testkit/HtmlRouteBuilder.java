package com.redhat.training.testkit;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class HtmlRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from( "direct:parseHtmlErrors" )
            .choice()
                .when().xpath( "//div[@id='warnings']" )
                    .to( "language:xpath://body/div/ul/li[1]/text()" )
                    .to( "file:out?fileName=latest-warning.txt" )
                .when().xpath( "//div[@id='errors']" )
                    .to( "language:xpath://article[1]/text()" )
                    .to( "file:out?fileName=latest-error.txt" );

    }

}