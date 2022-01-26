package com.redhat.training.health;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

import org.springframework.stereotype.Component;

/**
 * Camel route health indicator for the actuator plugin
 */
@Component
public class CamelRouteHealthIndicator implements HealthIndicator {

    @Autowired
    ProducerTemplate camelProducer;

    @Autowired
    RouteHealth routeHealth;

    @Override
    public Health health() {
        testRoute();

        String message = routeHealth.getStatusMessage();

        if ( routeHealth.isUp() ) {
            return Health
                    .up()
                    .withDetail( "status", message )
                    .build();
        }

        return Health
                .down()
                .withDetail( "status", message )
                .build();
    }

    private void testRoute() {
        try {
            camelProducer.sendBody( "direct:celsiusToFahrenheit", null );
        } catch( Exception e ) {
            System.out.println( "Route failed: " + e );
        }
    }

}
