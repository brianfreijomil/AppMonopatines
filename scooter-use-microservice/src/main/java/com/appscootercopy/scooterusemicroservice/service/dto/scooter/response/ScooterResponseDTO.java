package com.appscootercopy.scooterusemicroservice.service.dto.scooter.response;

import com.appscootercopy.scooterusemicroservice.domain.Scooter;
import com.appscootercopy.scooterusemicroservice.domain.Ubication;
import lombok.Data;

@Data
public class ScooterResponseDTO {

    private Long id;
    private Long licensePLate;
    private Boolean available;
    private Ubication ubication;

    public ScooterResponseDTO(Scooter scooter) {
        this.id = scooter.getId();
        this.licensePLate = scooter.getLicensePLate();
        this.ubication = scooter.getUbication();
        this.available = scooter.getAvailable();
    }

}
