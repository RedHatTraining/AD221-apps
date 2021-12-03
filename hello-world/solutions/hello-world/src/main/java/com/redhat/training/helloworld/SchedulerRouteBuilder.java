package com.redhat.training.helloworld;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//TODO: Enable the route by extending the RouteBuilder superclass
@Component
public class SchedulerRouteBuilder extends RouteBuilder {

    //TODO: Implement the configure method
    @Override
    public void configure() throws Exception {

	from("scheduler:myScheduler?delay=2000")
	    .routeId("Java DSL route")
	    .setBody().simple("Current time is ${header.CamelTimerFiredTime}") 
            .log("Sending message to the body logging route")
            .to("direct:log_body");
    }
}
