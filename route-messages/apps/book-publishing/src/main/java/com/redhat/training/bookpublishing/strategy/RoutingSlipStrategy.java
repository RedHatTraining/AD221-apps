package com.redhat.training.bookpublishing.strategy;

import org.apache.camel.language.XPath;

import java.util.ArrayList;

public class RoutingSlipStrategy {
    public String compute(
            @XPath(value="/book/bookinfo/productname/text()") String type
    ) {
        // TODO: Create a strategy for the review pipeline
        return "";
    }
}
