package com.redhat.training.health.route.filter;

import com.redhat.training.health.model.CovidCase;

import org.joda.time.DateTime;

public class CovidCaseFilter {

    public boolean isLastDayOfWeek(CovidCase covidCase) {
        return new DateTime(covidCase.getDate()).dayOfWeek().get() == 7;
    }
    
}
