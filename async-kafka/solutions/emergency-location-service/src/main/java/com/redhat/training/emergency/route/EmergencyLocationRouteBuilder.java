package com.redhat.training.emergency.route;

import com.redhat.training.emergency.model.Location;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.BindyType;
import org.springframework.stereotype.Component;

@Component
public class EmergencyLocationRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        onException(Exception.class).setBody(constant("errorOccured")).maximumRedeliveries(0).continued(true);

        from("file://../resources/data?fileName=locations.csv&noop=true")
        .routeId("emergency-location-route")
        .transform(body())
        .unmarshal()
        .bindy(BindyType.Csv, Location.class)
        .split(body())
        // TODO: Produce to Location data to Kafka instead of writing it to the database
        .to("kafka:locations")
        .to("direct:logger");


        // TODO: Consume Location data from `locations` Kafka topic and write it to the database
        from("kafka:locations")
        .routeId("kafka-consumer-route")
        .setBody(simple("insert into locations values('${body.latitude}','${body.longitude}')"))
        .to("jdbc:dataSource")
        .to("direct:logger");

        from("direct:logger")
        .log("Location data transferred");

    }
}
