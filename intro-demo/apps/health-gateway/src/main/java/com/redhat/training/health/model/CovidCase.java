package com.redhat.training.health.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

@Entity
@Table(name = "covid_cases")
@CsvRecord(separator=",", skipFirstLine = true, skipField =true)
public class CovidCase implements Serializable {

	private static final long serialVersionUID = -1L;

    // Date,iso3,CountryName,lat,lon,CumulativePositive,CumulativeDeceased,CumulativeRecovered,CurrentlyPositive,Hospitalized,IntensiveCare,EUcountry,EUCPMcountry,NUTS

    @Id
    @GeneratedValue
    private int id;
   
    @DataField(pos=1, pattern="yyyy-MM-dd")
	private Date date;

    @DataField(pos=3)
    private String countryName;

    @DataField(pos=6)
    private Integer cumulativePositive;

    @DataField(pos=7)
    private Integer cumulativeDeceased;

    @DataField(pos=8)
    private Integer cumulativeRecovered;

    @DataField(pos=14)
    private String nuts;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getNuts() {
        return nuts;
    }

    public void setNuts(String nuts) {
        this.nuts = nuts;
    }

    public String getYearWeekISO() {
		return ISODateTimeFormat.weekyearWeek().print(new DateTime(this.date));
    }

}