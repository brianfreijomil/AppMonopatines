package com.appscootercopy.scooterusemicroservice.service;
import com.appscootercopy.scooterusemicroservice.domain.PauseTrip;
import com.appscootercopy.scooterusemicroservice.domain.ScooterTrip;
import com.appscootercopy.scooterusemicroservice.domain.Tariff;
import com.appscootercopy.scooterusemicroservice.domain.Trip;
import com.appscootercopy.scooterusemicroservice.repository.TariffRepository;
import com.appscootercopy.scooterusemicroservice.service.dto.tariff.TariffRequestDTO;
import com.appscootercopy.scooterusemicroservice.service.exception.PauseActiveException;
import com.appscootercopy.scooterusemicroservice.service.timer.TimerPause;
import com.appscootercopy.scooterusemicroservice.repository.ScooterTripRepository;
import com.appscootercopy.scooterusemicroservice.repository.TripRepository;
import com.appscootercopy.scooterusemicroservice.service.dto.scooterTrip.response.ScooterTripResponseDTO;
import com.appscootercopy.scooterusemicroservice.service.dto.trip.request.TripRequestDTO;
import com.appscootercopy.scooterusemicroservice.service.dto.trip.response.TripResponseDTO;
import com.appscootercopy.scooterusemicroservice.service.exception.ConflictExistException;
import com.appscootercopy.scooterusemicroservice.service.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("TripService")
public class TripService {

    private TripRepository tripRepository;
    private ScooterTripRepository scooterTripRepository;
    private TariffRepository tariffRepository;

    public TripService(TripRepository t,ScooterTripRepository str, TariffRepository tariffRepository) {
        this.tripRepository=t;
        this.scooterTripRepository=str;
        this.tariffRepository=tariffRepository;
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
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        else {
            throw new NotFoundException("Trip", "Id", id);
        }
    }

    @Transactional(readOnly = true)
    public ScooterTripResponseDTO findScooterTripByTripId(Long id) {
        ScooterTrip scooterTrip = this.scooterTripRepository.findById_IdTrip_Id(id);
        if(scooterTrip!=null) {
            return new ScooterTripResponseDTO(scooterTrip);
        }
        throw new NotFoundException("ScooterTrip", "IdTrip", id);
    }

    @Transactional
    public ResponseEntity saveTariff(TariffRequestDTO request) {
        if(!this.tariffRepository.existsByType(request.getType())){
            this.tariffRepository.save(new Tariff(request.getCost(), request.getAvailable(), request.getType()));
            return new ResponseEntity(request.getType(), HttpStatus.CREATED);
        }

        throw new ConflictExistException("Tariff", "Type", request.getType());
    }

    @Transactional
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void initPause(Long id) {
        Optional<Trip> trip = this.tripRepository.findById(id);
        if(!trip.isEmpty()) {
            PauseTrip pauseTrip = trip.get().getPause();
            if(pauseTrip == null) {
                trip.get().setTimer(new TimerPause(trip.get().getId(), this.tripRepository, this.tariffRepository));
                trip.get().getTimer().initPause();
            }
            else {
                throw new ConflictExistException("PauseTrip", "Id", pauseTrip.getId());
            }
        }
        else {
            throw new NotFoundException("Trip", "Id", id);
        }
    }

    @Transactional
    public ResponseEntity endPause(Long id) {
        Optional<Trip> trip = this.tripRepository.findById(id);
        if(!trip.isEmpty()) {
            PauseTrip pauseTrip = trip.get().getPause();
            if(pauseTrip != null) {
                if(pauseTrip.getEndPause() == null) {
                    Time endPause = Time.valueOf(LocalTime.now());
                    Time initPause = pauseTrip.getInitPause();
                    Long diff = endPause.getTime() - initPause.getTime();
                    pauseTrip.setTimePause(diff/1000);
                    pauseTrip.setEndPause(endPause);
                    this.tripRepository.save(trip.get());
                    return new ResponseEntity(HttpStatus.NO_CONTENT);
                }
                else {
                    throw new PauseActiveException("PauseTrip", "id", pauseTrip.getId());
                }
            }
            else {
                throw new NotFoundException("PauseTrip", "Id", pauseTrip.getId());
            }
        }
        else {
            throw new NotFoundException("Trip", "Id", id);
        }

    }


}
