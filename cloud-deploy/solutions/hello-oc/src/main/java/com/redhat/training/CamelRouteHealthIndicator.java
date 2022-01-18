package com.redhat.training;

import java.time.Duration;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.apache.camel.CamelContext;
import org.apache.camel.health.HealthCheck;
import org.apache.camel.health.HealthCheckConfiguration;
import org.apache.camel.health.HealthCheckHelper;
import org.apache.camel.health.HealthCheck.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Check the Camel context readiness
 */
@Component
public class CamelRouteHealthIndicator implements HealthIndicator {

    private final String messageKey = "Camel route";

    @Autowired
    ApplicationContext appContext;

    @Autowired
    CamelContext context;

    @Autowired
    RouteHealthChecker routeChecker;

    @PostConstruct
    public void init() {
        // We must enable our Camel route checker so that it runs when
        // we call "HealthCheckHelper.invoke"
        // There must be a way to do this in the application.properties file, but I couldn't find this way
        HealthCheckConfiguration config = new HealthCheckConfiguration();
        config.setEnabled(true);
        config.setInterval(Duration.ofSeconds(1));
        config.setFailureThreshold(1);
        routeChecker.setConfiguration(config);
    }

    @Override
    public Health health() {
        // Invoke all the enabled Camel Health Checks
        Collection<Result> healthCheckResults = HealthCheckHelper.invoke(context);

        if (healthCheckResults.stream().anyMatch(e -> e.getState() != HealthCheck.State.UP)) {
            AvailabilityChangeEvent.publish(appContext, ReadinessState.REFUSING_TRAFFIC);
            return Health.down().withDetail(messageKey, healthCheckResults).build();
        }

        AvailabilityChangeEvent.publish(appContext, ReadinessState.ACCEPTING_TRAFFIC);
        return Health.up().withDetail(messageKey, healthCheckResults).build();
    }

}
