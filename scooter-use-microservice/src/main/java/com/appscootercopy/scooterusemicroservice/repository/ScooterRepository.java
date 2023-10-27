package com.appscootercopy.scooterusemicroservice.repository;

import com.appscootercopy.scooterusemicroservice.domain.Scooter;
import com.appscootercopy.scooterusemicroservice.domain.Ubication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScooterRepository extends JpaRepository<Scooter, Long> {

    Scooter findByLicensePLate(String licensePlate);
    Boolean existsByLicensePLate(String licensePlate);

    @Query("SELECT s FROM Scooter s JOIN fetch s.ubication")
    List<Scooter> findAllfetchingUbication();


}
