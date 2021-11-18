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
		.setBody().simple("Current time is ${header.CamelTimerFiredTime}") 
		.log("Hello World.  Body: ${body}")
		.to("mock:end");
    }
}
