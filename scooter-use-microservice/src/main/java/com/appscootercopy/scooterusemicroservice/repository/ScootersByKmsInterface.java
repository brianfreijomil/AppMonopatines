package com.appscootercopy.scooterusemicroservice.repository;

public interface ScootersByKmsInterface {

    public Long getId();
    public String getLicensePlate();
    public Boolean getAvailable();
    public Long getCountTrips();
    public Double getKms();
}
