package com.appscootercopy.scooterusemicroservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ScooterTripId implements Serializable {

    @Column( name = "id_scooter")
    private Long idScooter;
    @Column( name = "id_trip")
    private Long idTrip;

    public ScooterTripId(Long idScooter, Long idTrip) {
        super();
        this.idScooter=idScooter;
        this.idTrip=idTrip;
    }
}
