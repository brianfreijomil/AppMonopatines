package com.appscooter.tripmicroservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainTest {

    private TripService tripService;

    @Autowired
    public MainTest(TripService ts) {
        this.tripService=ts;
    }

    public void loadTest() {
        this.testPausa();
    }

    public void testPausa() {
        this.tripService.initPause(11L);
    }

}
