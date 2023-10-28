package com.appscootercopy.scooterusemicroservice.service;
import com.appscootercopy.scooterusemicroservice.domain.ScooterTrip;
import com.appscootercopy.scooterusemicroservice.domain.Trip;
import com.appscootercopy.scooterusemicroservice.repository.ScooterTripRepository;
import com.appscootercopy.scooterusemicroservice.repository.TripRepository;
import com.appscootercopy.scooterusemicroservice.service.dto.trip.request.TripRequestDTO;
import com.appscootercopy.scooterusemicroservice.service.dto.trip.response.TripResponseDTO;
import com.appscootercopy.scooterusemicroservice.service.exception.ConflictExistException;
import com.appscootercopy.scooterusemicroservice.service.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("TripService")
public class TripService {

    private TripRepository tripRepository;
    private ScooterTripRepository scooterTripRepository;

    public TripService(TripRepository t,ScooterTripRepository str) {
        this.tripRepository=t;
        this.scooterTripRepository=str;
    }

    @Transactional(readOnly = true)
    public TripResponseDTO findTripById(long id) {
        return tripRepository.findById(id)
                .map(TripResponseDTO::new)
                .orElseThrow(() -> new NotFoundException("Trip", "Id", id));
    }

    @Transactional(readOnly = true)
    public List<TripResponseDTO> findAllTrip() {
        List<Trip> trips = tripRepository.findAll();
        return trips.stream().map(s1-> new TripResponseDTO(s1)).collect(Collectors.toList());
    }

    @Transactional
    public ResponseEntity saveTrip(TripRequestDTO request) {
        if(!this.tripRepository.existsById(request.getId())){
            this.tripRepository.save(new Trip(request));
            return new ResponseEntity(request.getId(), HttpStatus.CREATED);
        }

        throw new ConflictExistException("Trip", "ID", request.getId());
    }

    @Transactional
    public ResponseEntity updateTrip(TripRequestDTO request, Long id) {
        Optional<Trip> tripExisting = this.tripRepository.findById(id);
        System.out.println(tripExisting);
        if(!tripExisting.isEmpty()){

            if(!this.tripRepository.existsById(request.getId())) {
                tripExisting.get().setKms(request.getKms());
                tripExisting.get().setInitTime(request.getInitTime());
                tripExisting.get().setEndTime(request.getEndTime());
                tripExisting.get().setEnded(request.getEnded());
                return new ResponseEntity(id, HttpStatus.ACCEPTED);
            }
            else {
                throw new ConflictExistException("Trip", "Id", request.getId());
            }

        }
        throw new NotFoundException("Trip", "Id", id);
    }

    @Transactional
    public ResponseEntity deleteTrip(Long id) {
        if(this.tripRepository.existsById(id)){
            ScooterTrip scooterTrip = this.scooterTripRepository.findById_IdTrip_Id(id);
            if(scooterTrip!=null) {
                this.scooterTripRepository.delete(scooterTrip);
            }
            this.tripRepository.deleteById(id);
            return new ResponseEntity(id, HttpStatus.NO_CONTENT);
        }
        else {
            throw new NotFoundException("Trip", "Id", id);
        }
    }

}
