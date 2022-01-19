package com.redhat.training.bookpublishing.healthcheck;

import org.apache.camel.health.HealthCheckResultBuilder;
import org.apache.camel.impl.health.AbstractHealthCheck;

import java.util.Map;

public class ApplicationHealthCheck extends AbstractHealthCheck {

    private volatile long firstCallTimeMillis = 0;

    public ApplicationHealthCheck() {
        super("book-publishing", "too-long");
    }

    @Override
    protected void doCall(HealthCheckResultBuilder builder, Map<String, Object> options) {
        builder.detail("toolong", "Reports DOWN when run for too long");
        if (firstCallTimeMillis == 0) {
            builder.unknown();
            firstCallTimeMillis = System.currentTimeMillis();
        } else if ((System.currentTimeMillis() - firstCallTimeMillis) < 10 * 1000L) {
            builder.up();
        } else {
            builder.down();
        }
    }
}
