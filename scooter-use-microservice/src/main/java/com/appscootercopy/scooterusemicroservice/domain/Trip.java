package com.appscootercopy.scooterusemicroservice.domain;

import com.appscootercopy.scooterusemicroservice.service.dto.trip.request.TripRequestDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Timestamp initTime;
    @Column(nullable = true)
    private Timestamp endTime;
    @Column(nullable = false)
    private Double kms;
    @Column(nullable = false)
    private Boolean ended;
    //@Column
    //private Timer pause; REVIEW WITH TEACHER

    public Trip(TripRequestDTO requestDTO) {
        this.initTime = requestDTO.getInitTime();
        this.endTime = requestDTO.getEndTime();
        this.kms = requestDTO.getKms();
        this.ended = requestDTO.getEnded();
    }
}
