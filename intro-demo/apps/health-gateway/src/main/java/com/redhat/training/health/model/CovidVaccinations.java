package com.redhat.training.health.model;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "records")
@XmlAccessorType(XmlAccessType.FIELD)
public class CovidVaccinations implements Serializable {

    private static final long serialVersionUID = -1L;

    @XmlElement(name = "record", type = CovidVaccination.class)
    private List<CovidVaccination> vaccinations;

	public List<CovidVaccination> getVaccinations() {
		return vaccinations;
	}

	public void setVaccinations(List<CovidVaccination> vaccinations) {
		this.vaccinations = vaccinations;
	}



}
