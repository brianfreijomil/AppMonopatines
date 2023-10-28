package com.scooter.apiGateway.controller;

import com.scooter.apiGateway.service.Constants;
import com.scooter.apiGateway.service.ScooterService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scooter")
public class ScooterController {

    private ScooterService scooterService;

    public ScooterController(ScooterService scooterService) {
        this.scooterService = scooterService;
    }

    @PutMapping("/{licensePlate}/setMaintenance")
    public ResponseEntity setMaintenance(@PathVariable String licensePlate, @RequestBody boolean setMaintenance) {
        System.out.println("llego");
        return scooterService.setMaintenance(licensePlate, setMaintenance);
    }

    @GetMapping("/{licensePlate}")
    public ScooterResponseDTO getScooterByLicensePlate(@PathVariable String licensePlate){
        return scooterService.findScooterByLicensePlate(licensePlate);
    }

    @GetMapping("/")
    public List<ScooterResponseDTO> getAllScooter(){
        return this.scooterService.findAllScooter();
    }

    @PostMapping("")
    public ResponseEntity saveScooter(@RequestBody @Valid ScooterRequestDTO request){
        return scooterService.saveScooter(request);
    }

    @DeleteMapping("/{id}")
    public void deleteScooter(@PathVariable Long id){
        this.scooterService.deleteScooter(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateScooter(@RequestBody @Valid ScooterRequestDTO request, @PathVariable Long id){
        return this.scooterService.updateScooter(request, id);
    }

    @GetMapping("/stops/{ubicationId}")
    public ScooterStopResponseDTO getScooterStopByUbication(@PathVariable Long ubicationId){
        return scooterService.findScooterStopByUbication(ubicationId);
    }

    @GetMapping("/stops/")
    public List<ScooterStopResponseDTO> getAllScooterStop(){
        return this.scooterService.findAllScooterStop();
    }

    @PostMapping("/stops")
    public ResponseEntity saveScooterStop(@RequestBody @Valid ScooterStopRequestDTO request) {
        return this.scooterService.saveScooterStop(request);
    }

    @DeleteMapping("/stops/{id}")
    public void deleteScooterStop(@PathVariable Long id){
        this.scooterService.deleteScooterStop(id);
    }

    @PutMapping("/stops/{id}")
    public ResponseEntity updateScooterStop(@RequestBody @Valid ScooterStopRequestDTO request, @PathVariable Long id){
        return this.scooterService.updateScooterStop(request, id);
    }

    @GetMapping("/{id}/trip/{idTrip}")
    public ScooterTripResponseDTO getScooterTripById(@PathVariable Long id, @PathVariable Long idTrip) {
        return this.scooterService.findScooterTripById(id,idTrip);
    }

    @GetMapping("/trips")
    public List<ScooterTripResponseDTO> getAllScooterTrip() {
        return this.scooterService.findAllScooterTrip();
    }

    @GetMapping("/{id}/trips")
    public List<ScooterTripResponseDTO> getAllScooterTripByScooterId(@PathVariable Long id) {
        return this.scooterService.findAllScooterTripByScooterId(id);
    }

    @PostMapping("/trip")
    public ResponseEntity saveScooterTrip(@RequestBody @Valid ScooterTripRequestDTO request) {
        return this.scooterService.saveScooterTrip(request);
    }

    @GetMapping("/ubications/{id}")
    public UbicationResponseDTO getUbicationById(@PathVariable Long id){
        return scooterService.findUbicationById(id);
    }

    @GetMapping("/ubications/")
    public List<UbicationResponseDTO> getAllUbication(){
        return this.scooterService.findAllUbication();
    }
}
