package com.redhat.training.bookpublishing;

import org.apache.camel.language.XPath;

import java.util.ArrayList;

public class RoutingSlipStrategy {
    public String compute(
            @XPath(value="/book/bookinfo/productname/text()") String type
    ) {
        ArrayList<String> destinations = new ArrayList<>();

        switch (type) {
            case "technical":
                destinations.add("file://data/pipeline/graphic-designer");
                // No break
            case "novel":
                destinations.add("file://data/pipeline/editor");
        }

        return String.join(",", destinations);
    }
}
