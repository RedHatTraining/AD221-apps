/**
 *  Copyright 2005-2016 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package com.redhat.training;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.RuntimeCamelException;
import org.springframework.stereotype.Component;


/**
 * A spring-boot application that includes a Camel route builder to setup the Camel routes
 */
@Component
public class MyRouteBuilder extends RouteBuilder {

    // @Autowired
    // RoutesHealthCheck routeChecker;

    @Override
    public void configure() throws Exception {

        onException(RuntimeCamelException.class)
            .bean("my-health-check", "processError");

        from("timer://foo?period=1000")
            .setBody().constant("Hello Worldddd")
            .process(e -> {
                if (Math.random() < 0.5) {
                    throw new RuntimeCamelException("This is a forced exception to have health check monitor this failure (route=bar)");
                }
            })
            .log(">>> ${body}")
            .bean("my-health-check", "success");

        // from("netty:tcp://example:8085").to("log:dummy").routeId("netty");
    }
}
