package edu.nd.phr;

/**
 * Created by jackyry830 on 7/3/2015.
 * POJO class to hold cholesterol information
 */

public class CholesterolInformation {
    private String date;
    private double hdl;
    private double ldl;
    private double triGlycaride;
    private String unit;

    public CholesterolInformation(){

    }
    public CholesterolInformation(String date, double hdl, double ldl, double triGlycaride, String unit) {
        this.date = date;
        this.hdl = hdl;
        this.ldl = ldl;
        this.triGlycaride = triGlycaride;
        this.unit = unit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getHdl() {
        return hdl;
    }

    public void setHdl(double hdl) {
        this.hdl = hdl;
    }

    public double getLdl() {
        return ldl;
    }

    public void setLdl(double ldl) {
        this.ldl = ldl;
    }

    public double getTriGlycaride() {
        return triGlycaride;
    }

    public void setTriGlycaride(double triGlycaride) {
        this.triGlycaride = triGlycaride;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
