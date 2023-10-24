package com.appscootercopy.scooterusemicroservice.controller;

import com.appscootercopy.scooterusemicroservice.service.ScooterService;
import com.appscootercopy.scooterusemicroservice.service.dto.scooter.request.ScooterRequestDTO;
import com.appscootercopy.scooterusemicroservice.service.dto.scooter.response.ScooterResponseDTO;
import com.appscootercopy.scooterusemicroservice.service.dto.scooterStop.request.ScooterStopRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/scooters")
public class ScooterController {

    private ScooterService scooterService;

    public ScooterController(ScooterService scooterService) {
        this.scooterService = scooterService;
    }

    @PostMapping("")
    public ResponseEntity saveScooter(@RequestBody @Valid ScooterRequestDTO request){
        return scooterService.saveScooter(request);
    }

    @DeleteMapping("/{id}")
    public void deleteScooter(@RequestBody @Valid Long id){
        this.scooterService.deleteScooter(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateScooter(@RequestBody @Valid ScooterRequestDTO scooter, @PathVariable Long idScooter){
        return this.scooterService.updateScooter(scooter, idScooter);
    }

    @GetMapping("/{id}")
    public ScooterResponseDTO getScooterById(@PathVariable Long id){
        return scooterService.findScooterById(id);
    }

    @GetMapping("/")
    public List<ScooterResponseDTO> getAllScooter(){
        return this.scooterService.findAllScooter();
    }

    @PostMapping("/stops")
    public ResponseEntity createScooterStop(@RequestBody @Valid ScooterStopRequestDTO stop){
        return this.scooterService.createScooterStop(stop);
    }
}
