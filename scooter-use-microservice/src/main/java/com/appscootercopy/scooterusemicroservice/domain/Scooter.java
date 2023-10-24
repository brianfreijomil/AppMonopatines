package com.appscootercopy.scooterusemicroservice.domain;

import com.appscootercopy.scooterusemicroservice.service.dto.scooter.request.ScooterRequestDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Scooter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Boolean available;
    @Column(nullable = false)
    @ManyToOne
    private Ubication ubication;

    public Scooter(ScooterRequestDTO requestDTO) {
        this.id = requestDTO.getId();
        this.available = requestDTO.getAvailable();
        this.ubication = requestDTO.getUbication();
    }
}
