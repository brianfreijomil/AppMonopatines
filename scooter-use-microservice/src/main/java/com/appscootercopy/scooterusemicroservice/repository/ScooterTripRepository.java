package com.appscootercopy.scooterusemicroservice.repository;

import com.appscootercopy.scooterusemicroservice.domain.ScooterTrip;
import com.appscootercopy.scooterusemicroservice.domain.ScooterTripId;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScooterTripRepository extends JpaRepository<ScooterTrip, ScooterTripId> {
    @Query("SELECT st FROM ScooterTrip st WHERE st.id.idScooter.id =:idScooter")
    List<ScooterTrip> findAllById_IdScooterId(@Param("idScooter") Long id);

    @Query("SELECT st FROM ScooterTrip st WHERE st.id.idTrip.id =:tripId")
    ScooterTrip findById_IdTrip_Id(@Param("tripId") Long id);

}
