package com.redhat.training.health.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "record")
@XmlAccessorType(XmlAccessType.FIELD)
public class CovidVaccination implements Serializable {

    private static final long serialVersionUID = -1L;

    @XmlElement(name = "Region")
    private String region;

    @XmlElement(name = "FirstDose")
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