package com.redhat.training.health.route.aggregation;

import com.redhat.training.health.model.CovidCase;
import com.redhat.training.health.model.CovidData;
import com.redhat.training.health.model.CovidVaccination;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class CovidDataAggregationStrategy implements AggregationStrategy{

	@Override
	public Exchange aggregate(Exchange original, Exchange resource) {
		CovidCase covidCase = original.getIn().getBody(CovidCase.class);
        CovidVaccination covidVaccination = resource.getIn().getBody(CovidVaccination.class);
        CovidData covidData = new CovidData();

            if(covidCase.getNuts().equals(covidVaccination.getRegion()) &&
                covidCase.getYearWeekISO().equals(covidVaccination.getYearWeekISO())
            ){
            covidData.setCountryCode(covidVaccination.getRegion());
            covidData.setCountryName(covidCase.getCountryName());
            covidData.setCumulativePositive(covidCase.getCumulativePositive());
            covidData.setCumulativeDeceased(covidCase.getCumulativeDeceased());
            covidData.setCumulativeRecovered(covidCase.getCumulativeRecovered());
            covidData.setFirstDose(covidVaccination.getFirstDose());
            covidData.setFirstDose(covidVaccination.getSecondDose());
            covidData.setYearWeekISO(covidVaccination.getYearWeekISO());
            covidData.setVaccine(covidVaccination.getVaccine());
        }

        original.getIn().setBody(covidData);

        return original;
	}
    
}
