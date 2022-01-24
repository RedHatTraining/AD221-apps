package com.redhat.training.bookpublishing.integration;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.awaitility.Awaitility.await;

abstract public class EndpointTest {
    public void waitForProcessingSampleFiles() {
        AtomicInteger processedSampleFiles = new AtomicInteger();

        await().atMost(10L, TimeUnit.SECONDS).pollDelay(1, TimeUnit.SECONDS).until(() -> {
            String log = Files.readString(Paths.get("target/quarkus.log"));

            if (log.contains(" File: ")) {
                processedSampleFiles.getAndIncrement();
            }

            return processedSampleFiles.intValue() == 4;
        });
    }
}
