package com.appscootercopy.scooterusemicroservice.service;
import com.appscootercopy.scooterusemicroservice.domain.*;
import com.appscootercopy.scooterusemicroservice.repository.*;
import com.appscootercopy.scooterusemicroservice.service.dto.scooter.request.ScooterRequestDTO;
import com.appscootercopy.scooterusemicroservice.service.dto.scooter.response.ScooterResponseDTO;
import com.appscootercopy.scooterusemicroservice.service.dto.scooterStop.request.ScooterStopRequestDTO;
import com.appscootercopy.scooterusemicroservice.service.dto.scooterStop.response.ScooterStopResponseDTO;
import com.appscootercopy.scooterusemicroservice.service.dto.scooterTrip.request.ScooterTripRequestDTO;
import com.appscootercopy.scooterusemicroservice.service.exception.ConflictExistException;
import com.appscootercopy.scooterusemicroservice.service.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("ScooterService")
public class ScooterService {

    private ScooterRepository scooterRepository;
    private ScooterStopRepository scooterStopRepository;
    private ScooterTripRepository scooterTripRepository;
    private TripRepository tripRepository;
    private UbicationRepository ubicationRepository;

    public ScooterService(ScooterRepository s, ScooterStopRepository ss, ScooterTripRepository st,
                          TripRepository tr, UbicationRepository ur) {
        this.scooterRepository=s;
        this.scooterStopRepository=ss;
        this.scooterTripRepository=st;
        this.tripRepository=tr;
        this.ubicationRepository=ur;
    }

    @Transactional(readOnly = true)
    public ScooterResponseDTO findScooterByLicensePlate(String licensePlate) {
        Scooter scooterFound = scooterRepository.findByLicensePLate(licensePlate);
        if(scooterFound!=null) {
            return new ScooterResponseDTO(scooterFound);
        }

        throw new NotFoundException("Scooter", "licencePlate", licensePlate);
    }

    @Transactional(readOnly = true)
    public List<ScooterResponseDTO> findAllScooter() {
        List<Scooter> scooters = scooterRepository.findAll();
        return scooters.stream().map(s1-> new ScooterResponseDTO(s1)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ScooterResponseDTO> findAllScooterWithUbication() {
        List<Scooter> scooters = scooterRepository.findAllfetchingUbication();
        return scooters.stream()
                .map(s1-> new ScooterResponseDTO(s1)).collect(Collectors.toList());
    }

    @Transactional
    public ResponseEntity saveScooter(ScooterRequestDTO request) {
        if(!this.scooterRepository.existsByLicensePLate(request.getLicensePlate())) {
            this.scooterRepository.save(new Scooter(request));
            return new ResponseEntity(request.getLicensePlate(), HttpStatus.CREATED);
        }

        throw new ConflictExistException("Scooter", "licensePlate", request.getLicensePlate());
    }

    @Transactional
    public ResponseEntity deleteScooter(Long id){
        if(this.scooterRepository.existsById(id)) {
            List<ScooterTrip> scooterTrips = this.scooterTripRepository.findAllById_IdScooterId(id);
            if(!scooterTrips.isEmpty()) {
                for (ScooterTrip scooterTrip : scooterTrips) {
                    ScooterTripId pkST = scooterTrip.getId();
                    Long idTrip = pkST.getIdTrip().getId();
                    this.scooterTripRepository.deleteById(pkST);
                    this.tripRepository.deleteById(idTrip);
                }
            }
            this.scooterRepository.deleteById(id);
            return new ResponseEntity(id, HttpStatus.NO_CONTENT);
        }
        else {
            throw new NotFoundException("Scooter", "Id", id);
        }
    }

    @Transactional
    public ResponseEntity updateScooter(ScooterRequestDTO request, Long idScooter) {
        Optional<Scooter> scooterExisting = this.scooterRepository.findById(idScooter);
        if(!scooterExisting.isEmpty()){
            if(!this.scooterRepository.existsByLicensePLate(request.getLicensePlate())) {
                scooterExisting.get().setLicensePLate(request.getLicensePlate());
                scooterExisting.get().setAvailable(request.getAvailable());
                scooterExisting.get().getUbication().setX(request.getUbication().getX());
                scooterExisting.get().getUbication().setY(request.getUbication().getY());
                return new ResponseEntity(idScooter, HttpStatus.ACCEPTED);
            }
            else {
                throw new ConflictExistException("Scooter", "licensePlate", request.getLicensePlate());
            }
        }
        throw new NotFoundException("Scooter", "Id", idScooter);
    }

    /*-------------------------------------------------------------------------------------*/

    @Transactional
    public ResponseEntity saveScooterTrip(ScooterTripRequestDTO request) {
        Optional<Scooter> scooterReferenced = this.scooterRepository.findById(request.getScooterId());
        if(!scooterReferenced.isEmpty()) {
            Optional<Trip> tripReferenced = this.tripRepository.findById(request.getTripId());
            if(!tripReferenced.isEmpty()) {
                ScooterTripId idST = new ScooterTripId(scooterReferenced.get(),tripReferenced.get());
                System.out.println("llego hasta el repo");
                if(!this.scooterTripRepository.existsById(idST)){
                    this.scooterTripRepository.save(new ScooterTrip(idST));
                    return new ResponseEntity(idST, HttpStatus.CREATED);
                }
                throw new ConflictExistException("ScooterTrip", "IdScooter", request.getScooterId(), "IdTrip", request.getScooterId());
            }
            throw new NotFoundException("Trip", "Id", request.getScooterId());
        }
        throw new NotFoundException("Scooter", "Id", request.getScooterId());
    }

    /*-------------------------------------------------------------------------------------*/

    @Transactional(readOnly = true)
    public ScooterStopResponseDTO findScooterStopByUbication(Long ubicationId) {
        ScooterStop scooterStopFound = scooterStopRepository.findByUbicationId(ubicationId);
        if(scooterStopFound!=null) {
            return new ScooterStopResponseDTO(scooterStopFound);
        }
        throw new NotFoundException("ScooterStop", "ubication.id", ubicationId);
    }

    @Transactional(readOnly = true)
    public List<ScooterStopResponseDTO> findAllScooterStop() {
        List<ScooterStop> stops = scooterStopRepository.findAll();
        return stops.stream().map(ss-> new ScooterStopResponseDTO(ss))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ScooterStopResponseDTO> findAllScooterStopWithUbication() {
        List<ScooterStop> stops = scooterStopRepository.findAllfetchingUbication();
        return stops.stream()
                .map(ss-> new ScooterStopResponseDTO(ss)).collect(Collectors.toList());
    }

    @Transactional
    public ResponseEntity saveScooterStop(ScooterStopRequestDTO request) {
        Double x = request.getUbication().getX();
        Double y = request.getUbication().getY();
        //si no existe una parada con esa ubicacion
        if(this.scooterStopRepository.existsByUbicationXAndUbicationY(x,y) == null) {
            //crearia automaticamente la ubicacion de esa parada
            this.scooterStopRepository.save(new ScooterStop(request));
            //no tengo nada para devolver
            return new ResponseEntity("ubication: "+x+", "+y, HttpStatus.CREATED);
        }
        throw new ConflictExistException("ScooterStop", "ubicationX", x, "ubicationY", y);
    }

    @Transactional
    public ResponseEntity deleteScooterStop(Long idScooter) {
        if(this.scooterStopRepository.existsById(idScooter)) {
            this.scooterStopRepository.deleteById(idScooter);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        throw new NotFoundException("ScooterStop", "Id", idScooter);
    }

    @Transactional
    public ResponseEntity updateScooterStop(ScooterStopRequestDTO request, Long idScooterStop) {
        Optional<ScooterStop> scooterStopExisting = this.scooterStopRepository.findById(idScooterStop);
        if(!scooterStopExisting.isEmpty()){
            Double x = request.getUbication().getX();
            Double y = request.getUbication().getY();
            if(this.scooterStopRepository.existsByUbicationXAndUbicationY(x,y) == null) {
                scooterStopExisting.get().getUbication().setX(x);
                scooterStopExisting.get().getUbication().setY(y);
                return new ResponseEntity(idScooterStop, HttpStatus.ACCEPTED);
            }
            else {
                throw new ConflictExistException("ScooterStop", "ubicationX", x, "ubicationY", y);
            }
        }
        throw new NotFoundException("ScooterStop", "Id", idScooterStop);
    }

    /*-------------------------------------------------------------------------------------*/

    //ABM UBICATION
    // SAVE : AUTOMATICO CON SCOOTER Y SCOOTERSTOP
    // UPDATE : AUTOMATICO CON SCOOTER Y SCOOTERSTOP
    // DELETE : AUTOMATICO CON SCOOTER Y SOOTERSTOP


}
