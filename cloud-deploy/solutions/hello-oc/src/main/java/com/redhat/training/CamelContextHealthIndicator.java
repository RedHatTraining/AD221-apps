package com.redhat.training;

import org.apache.camel.CamelContext;

import org.apache.camel.spring.boot.actuate.health.CamelHealthIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CamelContextHealthIndicator implements HealthIndicator {

    private final String messageKey = "Camel route";

    @Autowired
    CamelContext context;

    @Autowired
    ApplicationContext appContext;

    @Override
    public Health health() {
        CamelHealthIndicator indicator = new CamelHealthIndicator(context);

        Health health = indicator.health();

        if (health.getStatus().getCode() != "UP") {
            AvailabilityChangeEvent.publish(appContext, ReadinessState.REFUSING_TRAFFIC);
            return Health.down().withDetail(messageKey, health).build();
        }
        AvailabilityChangeEvent.publish(appContext, ReadinessState.ACCEPTING_TRAFFIC);
        return Health.up().withDetail(messageKey, health).build();
    }

}
