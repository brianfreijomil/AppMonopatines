package com.appscootercopy.scooterusemicroservice.service;
import com.appscootercopy.scooterusemicroservice.domain.Scooter;
import com.appscootercopy.scooterusemicroservice.domain.ScooterStop;
import com.appscootercopy.scooterusemicroservice.repository.ScooterRepository;
import com.appscootercopy.scooterusemicroservice.repository.ScooterStopRepository;
import com.appscootercopy.scooterusemicroservice.repository.ScooterTripRepository;
import com.appscootercopy.scooterusemicroservice.service.dto.scooter.request.ScooterRequestDTO;
import com.appscootercopy.scooterusemicroservice.service.dto.scooter.response.ScooterResponseDTO;
import com.appscootercopy.scooterusemicroservice.service.dto.scooterStop.request.ScooterStopRequestDTO;
import com.appscootercopy.scooterusemicroservice.service.exception.ConflictExistException;
import com.appscootercopy.scooterusemicroservice.service.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("ScooterService")
public class ScooterService {

    private ScooterRepository scooterRepository;
    private ScooterStopRepository scooterStopRepository;
    private ScooterTripRepository scooterTripRepository;

    public ScooterService(ScooterRepository s, ScooterStopRepository ss, ScooterTripRepository st) {
        this.scooterRepository=s;
        this.scooterStopRepository=ss;
        this.scooterTripRepository=st;
    }

    @Transactional(readOnly = true)
    public ScooterResponseDTO findScooterById(Long id) {
        return scooterRepository.findById(id).map(ScooterResponseDTO::new).orElseThrow(() -> new NotFoundException("Scooter", "ID", id));
    }

    @Transactional(readOnly = true)
    public List<ScooterResponseDTO> findAllScooter() {
        List<Scooter> scooters = scooterRepository.findAll();
        return scooters.stream().map(s1-> new ScooterResponseDTO(s1)).collect(Collectors.toList());
    }

    @Transactional
    public ResponseEntity saveScooter(ScooterRequestDTO request) {
        if(!this.scooterRepository.existsById(request.getId())){
            this.scooterRepository.save(new Scooter(request));
            return new ResponseEntity(request.getId(), HttpStatus.CREATED);
        }
        throw new ConflictExistException("Scooter", "ID", request.getId());
    }

    public void deleteScooter(Long id){
        if(!this.scooterRepository.existsById(id)) {
            this.scooterRepository.deleteById(id);
        }
       else {
            throw new NotFoundException("Scooter", "Id", id);
        }
    }

    public ResponseEntity updateScooter(ScooterRequestDTO scooter, Long idScooter) {
        Scooter sc = this.scooterRepository.getById(idScooter);
        if(sc != null){
            sc.setAvailable(scooter.getAvailable());
            sc.setUbication(scooter.getUbication());
            return new ResponseEntity(idScooter, HttpStatus.OK);
        }
        throw new NotFoundException("Account", "Long", idScooter);
    }

    public ResponseEntity createScooterStop(ScooterStopRequestDTO stop) {
        if(!this.scooterRepository.findScooterByUbication(stop.getUbication())){
            this.scooterStopRepository.save(new ScooterStop(stop));
            return new ResponseEntity(stop, HttpStatus.CREATED);
        }
        throw new ConflictExistException("Scooter", "ID",);
    }
}
