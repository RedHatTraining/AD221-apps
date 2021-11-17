package com.redhat.training.health.service;

import com.redhat.training.health.model.CovidCase;
import com.redhat.training.health.model.CovidVaccination;
import com.redhat.training.health.repository.CovidCaseRepository;
import com.redhat.training.health.repository.CovidVaccinationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CovidService {

    @Autowired
    private CovidCaseRepository covidCaseRepository;

    @Autowired
    private CovidVaccinationRepository covidVaccinationRepository;

    public Iterable<CovidCase> getAllCovidCases() {
        return covidCaseRepository.findAll();
    }

    public Iterable<CovidVaccination> getAllCovidVaccinations() {
        return covidVaccinationRepository.findAll();
    }
    
}
