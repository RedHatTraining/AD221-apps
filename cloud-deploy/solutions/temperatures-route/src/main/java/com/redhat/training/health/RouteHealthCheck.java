package com.redhat.training.health;

import java.util.Map;

import org.apache.camel.Body;
import org.apache.camel.Headers;
import org.apache.camel.health.HealthCheckResultBuilder;
import org.apache.camel.impl.health.AbstractHealthCheck;
import org.springframework.stereotype.Component;

// https://rmannibucau.metawerx.net/camel-healthcheck-registry-for-kubernetes.html

@Component("route-health-check")
public final class RouteHealthCheck extends AbstractHealthCheck {

    // @Autowired
    // ApplicationContext appContext;

    protected boolean up = false;
    protected String statusMessage = "Route status unknown";

    protected RouteHealthCheck() {
        super("routes", "my-health-check");
    }

    @Override
    protected void doCall( HealthCheckResultBuilder builder, Map<String, Object> options ) {
        builder.unknown();

        builder.detail("checker", "checking liveness");

        if (up) {
            builder.up();
        } else {
            builder.down();
        }

    }

    public void down(@Headers Map headers, @Body String payload) {
        up = false;
        statusMessage = "Route is down. " + headers.get("error");
    }

    public void up() {
        up = true;
        statusMessage = "Route is up";
    }
}
