package com.redhat.training;

import java.util.Map;

import org.apache.camel.health.HealthCheckResultBuilder;
import org.apache.camel.impl.health.AbstractHealthCheck;
import org.springframework.stereotype.Component;

// https://rmannibucau.metawerx.net/camel-healthcheck-registry-for-kubernetes.html

@Component("my-health-check")
public final class RouteHealthChecker extends AbstractHealthCheck {

    // @Autowired
    // ApplicationContext appContext;

    protected boolean up = true;

    protected RouteHealthChecker() {
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

    public void processError() {
        up = false;
    }

    public void success() {
        up = true;
    }
}
