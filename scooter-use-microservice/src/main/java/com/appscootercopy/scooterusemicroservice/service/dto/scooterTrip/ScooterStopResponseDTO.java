package com.appscootercopy.scooterusemicroservice.service.dto.scooterTrip;

import com.appscootercopy.scooterusemicroservice.domain.ScooterStop;
import lombok.Data;

@Data
public class ScooterStopResponseDTO {

    private Long id;
    private String ubication;

    public ScooterStopResponseDTO(ScooterStop scooterStop) {
        this.id = scooterStop.getId();
        this.ubication = scooterStop.getUbication();
    }
}
