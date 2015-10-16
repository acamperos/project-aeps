package org.aepscolombia.platform.util;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author HSOTELO
 */
public class Location {
     /*Members Class*/
    private double latitude;
    private double longitude;
    private double uncertainty;
    
    /*Methods*/
    /**
     * Method Construct
     * @param latitude
     * @param longitude
     * @param uncertainty distance in meters
     */
    public Location(double latitude,double longitude,double uncertainty){
        this.latitude=latitude;
        this.longitude=longitude;
        this.uncertainty=uncertainty;
    }
    
    @Override
    public String toString(){
        return String.valueOf(latitude) + "|" + String.valueOf(longitude) + "|" + String.valueOf(uncertainty) ;
    }

    /*Properties*/
    /**
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the uncertainty
     */
    public double getUncertainty() {
        return uncertainty;
    }

    /**
     * @param uncertainty the uncertainty to set
     */
    public void setUncertainty(double uncertainty) {
        this.uncertainty = uncertainty;
    }
}
