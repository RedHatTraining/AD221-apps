package com.redhat.training.health.model;

import java.io.Serializable;

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
public class CovidVaccination implements Serializable {

    private static final long serialVersionUID = -1L;

    @Id
    @GeneratedValue
    private int id;

    @XmlElement(name = "Region")
    private String region;

    @XmlElement(name = "FirstDose")
    private Integer firstDose;

	@XmlElement(name = "SecondDose")
    private Integer secondDose;

    @XmlElement(name = "YearWeekISO")
    private String yearWeekISO;

	@XmlElement(name = "TargetGroup")
    private String targetGroup;

	@XmlElement(name = "Vaccine")
    private String vaccine;


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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getYearWeekISO() {
		return yearWeekISO;
	}

	public void setYearWeekISO(String yearWeekISO) {
		this.yearWeekISO = yearWeekISO;
	}

	public String getTargetGroup() {
		return targetGroup;
	}

	public void setTargetGroup(String targetGroup) {
		this.targetGroup = targetGroup;
	}

	public Integer getSecondDose() {
		return secondDose;
	}

	public void setSecondDose(Integer secondDose) {
		this.secondDose = secondDose;
	}

	public String getVaccine() {
		return vaccine;
	}

	public void setVaccine(String vaccine) {
		this.vaccine = vaccine;
	}

    

}
