package com.redhat.training.health.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "covid_vaccinations")
@XmlRootElement(name = "record")
@XmlAccessorType(XmlAccessType.FIELD)
public class CovidVaccination {

    @Id
    @GeneratedValue
    private int id;

    @XmlElement(name = "Region")
    private String region;

    @XmlElement(name = "FirstDose")
    private Integer firstDose;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
