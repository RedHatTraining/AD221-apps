package com.redhat.training.health.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "covid_vaccinations")
public class CovidVaccinationEntity implements Serializable {

    private static final long serialVersionUID = -1L;

    @Id
    @GeneratedValue
    private int id;

    private String region;

    private Integer firstDose;


    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

	public Integer getFirstDose() {
		return firstDose;
	}

	public void setFirstDose(Integer firstDose) {
		this.firstDose = firstDose;
	}

}
