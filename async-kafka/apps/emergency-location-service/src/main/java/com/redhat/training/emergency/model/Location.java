package com.redhat.training.emergency.model;

import java.io.Serializable;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator=",", skipField =true)
public class Location implements Serializable {

	private static final long serialVersionUID = -1L;
   
    @DataField(pos=1)
	private Double latitude;

    @DataField(pos=2)
    private Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "{ \"latitude\" : " + latitude + ", \"longitude\" : " + longitude + " }";
    }

    
}
