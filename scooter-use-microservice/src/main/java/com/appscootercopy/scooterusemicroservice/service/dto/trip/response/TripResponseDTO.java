package com.appscootercopy.scooterusemicroservice.service.dto.trip.response;

import com.appscootercopy.scooterusemicroservice.domain.PauseTrip;
import com.appscootercopy.scooterusemicroservice.domain.Tariff;
import com.appscootercopy.scooterusemicroservice.domain.Trip;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class TripResponseDTO {

    private Long id;
    private Timestamp initTime;
    private Timestamp endTime;
    private Double kms;
    private Boolean ended;
    private Long idPause;
    private Long idTariff;
    private Long idTariffExtra;

    public TripResponseDTO(Trip trip) {
        this.id=trip.getId();
        this.initTime=trip.getInitTime();
        this.endTime=trip.getEndTime();
        this.kms= trip.getKms();
        this.ended=trip.getEnded();
        this.idPause=trip.getPause().getId();
        this.idTariff=trip.getTariff().getId();
        this.idTariffExtra=trip.getTariffExtra().getId();
    }


}
