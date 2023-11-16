package com.appscootercopy.scooterusemicroservice.service;
import com.appscootercopy.scooterusemicroservice.domain.*;
import com.appscootercopy.scooterusemicroservice.repository.*;
import com.appscootercopy.scooterusemicroservice.repository.interfaces.ScootersByTripsAndYearInterface;
import com.appscootercopy.scooterusemicroservice.service.dto.scooter.request.EnableScooterRequestDTO;
import com.appscootercopy.scooterusemicroservice.service.dto.scooter.request.ScooterRequestDTO;
import com.appscootercopy.scooterusemicroservice.service.dto.scooter.request.TripsAndYearRequestDTO;
import com.appscootercopy.scooterusemicroservice.service.dto.scooter.response.*;
import com.appscootercopy.scooterusemicroservice.service.dto.scooterStop.request.ScooterStopRequestDTO;
import com.appscootercopy.scooterusemicroservice.service.dto.scooterStop.response.ScooterStopResponseDTO;
import com.appscootercopy.scooterusemicroservice.service.dto.ubication.request.UbicationRequestDTO;
import com.appscootercopy.scooterusemicroservice.service.dto.ubication.response.UbicationResponseDTO;
import com.appscootercopy.scooterusemicroservice.service.exception.ConflictExistException;
import com.appscootercopy.scooterusemicroservice.service.exception.NotFoundException;
import com.appscootercopy.scooterusemicroservice.service.exception.UniqueException;
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
    private UbicationRepository ubicationRepository;

    public ScooterService(ScooterRepository s, ScooterStopRepository ss,
                          UbicationRepository ur) {
        this.scooterRepository=s;
        this.scooterStopRepository=ss;
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
    public List<ScooterResponseDTO> findAllScooterFetchingUbication() {
        List<Scooter> scooters = scooterRepository.findAllfetchingUbication();
        return scooters.stream().map(s1-> new ScooterResponseDTO(s1)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ReportAvailabilityDTO findCountScooterByAvailability() {
        Long countAvailables = this.scooterRepository.findCountScootersAvailables(true).getCountScooters();
        Long countNotAvailables = this.scooterRepository.findCountScootersAvailables(false).getCountScooters();
        return new ReportAvailabilityDTO(countAvailables,countNotAvailables);
    }

    @Transactional(readOnly = true)
    public List<ScooterResponseDTO> findAllScooterCloseToMe(UbicationRequestDTO request) {
        List<Scooter> scooters = scooterRepository.findAllCloseToMe(request.getX(), request.getY(), 5.0, 5.0);
        return scooters.stream()
                .map(s1-> new ScooterResponseDTO(s1)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ScooterByTripsYearResponseDTO> findAllScooterByTripsAndYear(TripsAndYearRequestDTO request) {
        /*
        solicitar a trip-microservice (/api/scooters/trips&year)

        //si viene como un obj lo mapeas directamente o necesitas el requestDTO

        List<ScootersByTripsAndYearInterface> scooters =
        return scooters.stream()
                .map(s1-> new ScooterByTripsYearResponseDTO(s1.getLicensePlate(), s1.getAvailable(), s1.getCountTrips(), s1.getYear())).collect(Collectors.toList());
        */
        return null;
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
            /*
            * solicitud a trip-microservice (/api/trips/scooter/{licensePlate})
            * obtengo los trips
            * //si viene como un obj lo mapeas directamente o necesitas el requestDTO
            * y eliminar todos enviando un delete por el id de cada uno?
            * delete solicitud por cada registro a trip-microservice (/api/trips/{id})*/


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
            if(scooterExisting.get().getLicensePLate().equals(request.getLicensePlate())) {
                scooterExisting.get().setAvailable(request.getAvailable());
                scooterExisting.get().getUbication().setX(request.getUbication().getX());
                scooterExisting.get().getUbication().setY(request.getUbication().getY());
                return new ResponseEntity(idScooter, HttpStatus.ACCEPTED);
            }
            else {
                throw new UniqueException("Scooter", "licensePlate", request.getLicensePlate());
            }
        }
        throw new NotFoundException("Scooter", "Id", idScooter);
    }

    @Transactional
    public ScooterResponseDTO enableScooter(EnableScooterRequestDTO request, Long id) {
        Optional<Scooter> scooterExisting = this.scooterRepository.findById(id);
        if(!scooterExisting.isEmpty()){
            scooterExisting.get().setAvailable(request.getAvailable());
            return new ScooterResponseDTO(scooterExisting.get());
        }
        throw new NotFoundException("Scooter", "Id", id);
    }

    @Transactional(readOnly = true)
    public List<ReportUseScootersByKmsDTO> findUseScootersByKms() {

        //solicitar a trip-microservice (/api/trips/report/kms)
        ////si viene como un obj lo mapeas directamente o necesitas el requestDTO
        //luego obtener info de scooters y unir la informacion (si es necesario)

        /*
        return scooterTripRepository.findAllByKms()
                .stream()
                .map(r-> new ReportUseScootersByKmsDTO(r.getId(),r.getLicensePlate(),r.getAvailable(),r.getCountTrips(),r.getKms())).collect(Collectors.toList());
        */

        return null;
    }

    @Transactional(readOnly = true)
    public List<ReportUseScootersByTimeCcPauses> findUseScootersByTimeCcPauses() {
        /*
         * obtener info de trip-microservice (/api/trips/report/pauses)
         *si viene como un obj lo mapeas directamente o necesitas el requestDTO
         * luego obtener info de scooters y unir la informacion (si es necesario)
         *
        return scooterTripRepository.findAllByTimeCcPauses()
                .stream()
                .map(r-> new ReportUseScootersByTimeCcPauses(r.getId(),r.getLicensePlate(),r.getAvailable(),r.getCountTrips(),r.getKms())).collect(Collectors.toList());
        */
        return null;
    }

    @Transactional(readOnly = true)
    public List<ReportUseScootersByTimeOutPauses> findUseScootersByTimeOutPauses() {
        /*
         * obtener info de trip-microservice (/api/trips/report/non&pauses)
         *si viene como un obj lo mapeas directamente o necesitas el requestDTO
         * luego obtener info de scooters y unir la informacion (si es necesario)
         *
        return scooterTripRepository.findAllByTimeWithoutPauses()
                .stream()
                .map(r-> new ReportUseScootersByTimeOutPauses(r.getId(),r.getLicensePlate(),r.getAvailable(),r.getCountTrips(),r.getKms())).collect(Collectors.toList());
        */
        return null;
    }

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
        if(this.scooterStopRepository.existsByUbicationXAndUbicationY(x,y) == null) {
            this.scooterStopRepository.save(new ScooterStop(request));
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

    @Transactional(readOnly = true)
    public UbicationResponseDTO findUbicationById(Long id) {
        return ubicationRepository.findById(id)
                .map(UbicationResponseDTO::new)
                .orElseThrow(() -> new NotFoundException("Ubication", "Id", id));
    }

    @Transactional(readOnly = true)
    public List<UbicationResponseDTO> findAllUbication() {
        List<Ubication> ubications = ubicationRepository.findAll();
        return ubications.stream().map(u-> new UbicationResponseDTO(u)).collect(Collectors.toList());
    }

}
