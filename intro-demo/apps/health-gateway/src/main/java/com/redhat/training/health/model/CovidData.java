package com.redhat.training.health.model;

import java.io.Serializable;

public class CovidData implements Serializable {

    private static final long serialVersionUID = -1L;

    private String countryCode;
    private String countryName;
    private Integer cumulativePositive;
    private Integer cumulativeDeceased;
    private Integer cumulativeRecovered;
    private Integer firstDose;
	private Integer secondDose;
	private String yearWeekISO;
	private String vaccine;
    
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public Integer getCumulativePositive() {
		return cumulativePositive;
	}
	public void setCumulativePositive(Integer cumulativePositive) {
		this.cumulativePositive = cumulativePositive;
	}
	public Integer getCumulativeDeceased() {
		return cumulativeDeceased;
	}
	public void setCumulativeDeceased(Integer cumulativeDeceased) {
		this.cumulativeDeceased = cumulativeDeceased;
	}
	public Integer getCumulativeRecovered() {
		return cumulativeRecovered;
	}
	public void setCumulativeRecovered(Integer cumulativeRecovered) {
		this.cumulativeRecovered = cumulativeRecovered;
	}
	public Integer getFirstDose() {
		return firstDose;
	}
	public void setFirstDose(Integer firstDose) {
		this.firstDose = firstDose;
	}
	public Integer getSecondDose() {
		return secondDose;
	}
	public void setSecondDose(Integer secondDose) {
		this.secondDose = secondDose;
	}
	public String getYearWeekISO() {
		return yearWeekISO;
	}
	public void setYearWeekISO(String yearWeekISO) {
		this.yearWeekISO = yearWeekISO;
	}
	public String getVaccine() {
		return vaccine;
	}
	public void setVaccine(String vaccine) {
		this.vaccine = vaccine;
	}
	
    
    
}
