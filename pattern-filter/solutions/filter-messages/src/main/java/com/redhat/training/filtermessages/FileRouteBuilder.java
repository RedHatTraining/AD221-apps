package com.redhat.training.filtermessages;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        //TODO: Filter messages from ABC Company: /order/orderItems/orderItem[not(contains(orderItemPublisherName,'ABC Company'))]
        from("file:orders/incoming?include=order.*xml")
            .filter(xpath("/order/orderItems/orderItem[not(contains(orderItemPublisherName,'ABC Company'))]"))
                .to("file:orders/outgoing/?fileExist=Fail");
    }
}