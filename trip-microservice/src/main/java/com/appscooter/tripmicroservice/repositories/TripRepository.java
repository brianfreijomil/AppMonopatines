package com.appscooter.tripmicroservice.repositories;

import com.appscooter.tripmicroservice.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends JpaRepository<Trip,Long> {

}
