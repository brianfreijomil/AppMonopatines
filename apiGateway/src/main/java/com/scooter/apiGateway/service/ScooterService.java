package com.scooter.apiGateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ScooterService {

    private static final String URL = "http://localhost:8082";

    @Autowired
    private WebClient.Builder webClient;

    public ScooterService() {
    }


    public ResponseEntity setMaintenance(String licensePlate, boolean setMaintenance) {
        return webClient.
                build()
                .put()
                .uri(URL + "/api/maintenance/{licensePlate}/setMaintenance", licensePlate)
                .bodyValue(setMaintenance)
                .retrieve()
                .toEntity(String.class)
                .block();
    }
}
