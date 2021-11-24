package com.redhat.training.health.route.converter;

import com.redhat.training.health.model.CovidVaccination;
import com.redhat.training.health.model.CovidVaccinationEntity;

import org.apache.camel.Converter;
import org.apache.camel.Exchange;

@Converter
public class CovidVaccinationConverter {
    @Converter
	public static CovidVaccinationEntity modelToEntity(CovidVaccination order, Exchange exchange) {
		return new CovidVaccinationEntity();
	}
}
