package com.redhat.training.health;

import java.util.Map;

import org.apache.camel.Body;
import org.apache.camel.Headers;
import org.springframework.stereotype.Component;

/**
 * Basic bean to store the route status
 */
@Component("route-health")
public final class RouteHealth {

    private boolean up = true;
    private String statusMessage = "Route is up";

    public boolean isUp() {
        return up;
    }

    public String getStatusMessage() {
        return statusMessage;
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
