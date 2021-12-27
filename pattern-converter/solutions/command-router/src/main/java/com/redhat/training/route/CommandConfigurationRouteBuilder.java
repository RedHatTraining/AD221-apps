package com.redhat.training.route;

import com.redhat.training.model.CommandConfigurationCSVRecord;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.BindyType;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class CommandConfigurationRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("file:../resources/data?noop=true&fileName=config.csv")
        .unmarshal()
        .bindy(BindyType.Csv, CommandConfigurationCSVRecord.class)
        .split(body())
		.removeHeaders("CamelHttp*")
        .setHeader(Exchange.HTTP_METHOD, constant("POST"))
        .setHeader(Exchange.CONTENT_TYPE, constant(MediaType.TEXT_PLAIN_VALUE))
        .log("${body}")
        .to("http4://localhost:8081/commands");
    } 
}
