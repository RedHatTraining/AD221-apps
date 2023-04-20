package com.redhat.training.health.route.splitter;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.redhat.training.health.model.CovidVaccination;
import com.redhat.training.health.model.CovidVaccinations;

import org.apache.camel.Exchange;

public class CovidVaccinationXMLSplitter {

    public Collection<List<CovidVaccination>> split(Exchange exchange) {
        CovidVaccinations covidVaccinations = (CovidVaccinations) exchange.getMessage().getBody();

        final int chunkSize = 5000;
        final AtomicInteger counter = new AtomicInteger();

        final Collection<List<CovidVaccination>> covidVaccinationListCollection = covidVaccinations.getVaccinations().stream()
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / chunkSize)).values();

        return covidVaccinationListCollection;
    }

}
