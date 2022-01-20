package com.redhat.training.bookpublishing.integration;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;

@QuarkusTest
public class HealthCheckEndpointTest {
    @Test
    public void testHealthEndpoint() {
        RestAssured.get("/q/health")
                .then()
                .statusCode(200)
                .body(
                        "status", is("UP"),
                        "checks.status", contains("UP", "UP"),
                        "checks.name", containsInAnyOrder("camel-readiness-checks", "camel-liveness-checks"),
                        "checks.data.context", contains("UP")
                );
    }
}
