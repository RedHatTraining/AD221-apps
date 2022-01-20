package com.redhat.training.bookpublishing.integration;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class EditorPipelineEndpointTest extends EndpointTest {
    @Test
    public void testEditorEndpoint() {

        waitForProcessingSampleFiles();

        RestAssured.given()
                .when()
                    .get("/pipeline/editor")
                .then()
                    .body("size()", is(3));
    }
}
