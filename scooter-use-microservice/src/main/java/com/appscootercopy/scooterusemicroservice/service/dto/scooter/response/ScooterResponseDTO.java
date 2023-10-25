package com.appscootercopy.scooterusemicroservice.service.dto.scooter.response;

import com.appscootercopy.scooterusemicroservice.domain.Scooter;
import lombok.Data;

@Data
public class ScooterResponseDTO {

    private Long id;
    private Boolean available;
    private String ubication;

    public ScooterResponseDTO(Scooter scooter) {
        this.id = scooter.getId();
        this.ubication = scooter.getUbication();
        this.available = scooter.getAvailable();
    }

}
