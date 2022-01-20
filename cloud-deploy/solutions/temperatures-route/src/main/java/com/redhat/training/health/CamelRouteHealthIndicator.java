package com.redhat.training.health;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Camel route health indicator for the actuator plugin
 */
@Component
public class CamelRouteHealthIndicator implements HealthIndicator {

    private final String messageKey = "Route status";

    @Autowired
    ApplicationContext appContext;

    @Autowired
    ProducerTemplate camelProducer;

    @Autowired
    RouteHealthCheck routeHealthCheck;

    @Override
    public Health health() {

        Health health;
        ReadinessState state;

        testRoute();

        if (routeHealthCheck.up) {
            state = ReadinessState.ACCEPTING_TRAFFIC;
            health = Health.up().withDetail(messageKey, routeHealthCheck.statusMessage).build();
        } else {
            state = ReadinessState.REFUSING_TRAFFIC;
            health = Health.down().withDetail(messageKey, routeHealthCheck.statusMessage).build();
        }

        // Set readiness state
        AvailabilityChangeEvent.publish(appContext, state);

        return health;
    }

    private void testRoute() {
        try {
            camelProducer.sendBody("direct:celsiusToFahrenheit", null);
        } catch(Exception e) {
            System.out.println("Route failed: " + e);
        }
    }

}
