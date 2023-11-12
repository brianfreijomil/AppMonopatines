package com.appscootercopy.scooterusemicroservice.repository;

public interface ReportInterface {

    public Long getId();
    public String getLicensePlate();
    public Boolean getAvailable();
    public Long getCountTrips();
    public Double getKms();
}
