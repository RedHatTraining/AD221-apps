package com.redhat.training.downloader;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class HttpRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // TODO: use property placeholders
        from("direct:start")
            .to("http4://my-external-service/greeting")
            .to("file:out?fileName=response.txt");
    }
}
