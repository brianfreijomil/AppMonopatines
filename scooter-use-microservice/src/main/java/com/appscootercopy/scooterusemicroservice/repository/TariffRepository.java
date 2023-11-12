package com.appscootercopy.scooterusemicroservice.repository;

import com.appscootercopy.scooterusemicroservice.domain.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TariffRepository extends JpaRepository<Tariff, Long> {

    Boolean existsByType(Long type);

    @Query("SELECT t FROM Tariff t WHERE t.type =:type AND t.available =:available")
    Tariff findByTypeAndAvailable(@Param("type") Long type, @Param("available") Boolean available);
}
