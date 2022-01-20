package com.redhat.training.bookpublishing.integration;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GraphicDesignerPipelineEndpointTest extends EndpointTest {
    @Test
    public void testGraphicDesignerEndpoint() {

        waitForProcessingSampleFiles();

        RestAssured.given()
                .when()
                    .get("/pipeline/graphic-designer")
                .then()
                    .body("size()", is(2));
    }
}
