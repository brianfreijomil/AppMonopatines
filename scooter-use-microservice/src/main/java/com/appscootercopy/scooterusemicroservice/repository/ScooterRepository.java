package com.appscootercopy.scooterusemicroservice.repository;

import com.appscootercopy.scooterusemicroservice.domain.Scooter;
import com.appscootercopy.scooterusemicroservice.domain.Ubication;
import com.appscootercopy.scooterusemicroservice.service.dto.scooter.response.ReportAvailabilityDTO;
import com.appscootercopy.scooterusemicroservice.service.dto.scooter.response.ReportUseScootersByKmsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScooterRepository extends JpaRepository<Scooter, Long> {

    Scooter findByLicensePLate(String licensePlate);

    Boolean existsByLicensePLate(String licensePlate);

    @Query("SELECT COUNT(s.id) AS cant FROM Scooter s WHERE s.available =:state")
    ScootersAvailablesInterface findCountScootersAvailables(@Param("state") Boolean state);

    @Query("SELECT s FROM Scooter s JOIN fetch s.ubication")
    List<Scooter> findAllfetchingUbication();

    @Query("SELECT s FROM Scooter s " +
            "JOIN fetch s.ubication " +
            "WHERE (s.ubication.y between :y and :yLimit) " +
            "AND (s.ubication.x between :x and :xLimit)")
    List<Scooter> findAllCloseToMe(@Param("x") Double x, @Param("y") Double y, @Param("yLimit") Double yLimit, @Param("xLimit") Double xLimit);

}
