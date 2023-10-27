package com.appscootercopy.scooterusemicroservice.repository;

import com.appscootercopy.scooterusemicroservice.domain.ScooterTrip;
import com.appscootercopy.scooterusemicroservice.domain.ScooterTripId;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScooterTripRepository extends JpaRepository<ScooterTrip, ScooterTripId> {

    @Query("DELETE FROM ScooterTrip st WHERE st.id.idScooter =:idScooter")
    void deleteAllByIdScooter(@Param("idScooter") Long idScooter);
}
