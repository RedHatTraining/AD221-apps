package com.redhat.training.bookpublishing.strategy;

import org.apache.camel.language.XPath;

public class DynamicRoutingStrategy {
    public String compute(
            @XPath(value="/book/bookinfo/productname/text()") String type
    ) {
        return "file://data/printing-services/" + type;
    }
}
