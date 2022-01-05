package com.redhat.training.route;

import com.redhat.training.model.CommandConfigurationCSVRecord;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.BindyType;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class CommandConfigurationRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        onException(Exception.class).continued(true);
        
        from("file:../resources/data?noop=true&fileName=config.csv")
        .routeId("air-purifier-configuration-route")
        .unmarshal()
        .bindy(BindyType.Csv, CommandConfigurationCSVRecord.class)
        .split(body())
        // TODO: Comment-out or remove the following conversion to JSON
        //.marshal(new JacksonDataFormat(CommandConfigurationCSVRecord.class))
		.removeHeaders("CamelHttp*")
        .setHeader(Exchange.HTTP_METHOD, constant("POST"))
        .setHeader(Exchange.CONTENT_TYPE, constant(MediaType.TEXT_PLAIN_VALUE))
        .to("http4://localhost:8081/commands")
        .setBody(simple("${header.CamelHttpResponseCode}"))
        .to("direct:logReturnCode");

        from("direct:logReturnCode")
        .log("${body}");

    }
}
