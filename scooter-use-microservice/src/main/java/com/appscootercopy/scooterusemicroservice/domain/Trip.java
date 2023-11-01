package com.appscootercopy.scooterusemicroservice.domain;

import com.appscootercopy.scooterusemicroservice.service.timer.TimerPause;
import com.appscootercopy.scooterusemicroservice.service.dto.trip.request.TripRequestDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Trip {

    @Id
    private Long id;
    @Column(nullable = false)
    private Timestamp initTime;
    @Column(nullable = true)
    private Timestamp endTime;
    @Column(nullable = false)
    private Double kms;
    @Column(nullable = false)
    private Boolean ended;
    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    private PauseTrip pause;
    @Transient
    private TimerPause timer;
    @ManyToOne(fetch = FetchType.LAZY)
    private Tariff tariff;
    @ManyToOne(fetch = FetchType.LAZY)
    private Tariff tariffExtra;


    public Trip(TripRequestDTO requestDTO) {
        this.id = requestDTO.getId();
        this.initTime = requestDTO.getInitTime();
        this.endTime = requestDTO.getEndTime();
        this.kms = requestDTO.getKms();
        this.ended = requestDTO.getEnded();
        this.tariff = requestDTO.getTariff();
        this.timer = null;
        this.tariffExtra = null;
    }

    public Trip(Long id, Timestamp initTime, Timestamp endTime, Double kms, Boolean ended, Tariff tariff) {
        this.id = id;
        this.initTime = initTime;
        this.endTime = endTime;
        this.kms = kms;
        this.ended = ended;
        this.tariff = tariff;
        this.timer = null;
        this.tariffExtra = null;
    }

}
