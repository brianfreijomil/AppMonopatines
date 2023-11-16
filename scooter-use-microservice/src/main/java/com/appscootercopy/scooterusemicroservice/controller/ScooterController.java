package com.appscootercopy.scooterusemicroservice.controller;

import com.appscootercopy.scooterusemicroservice.service.ScooterService;
import com.appscootercopy.scooterusemicroservice.service.dto.scooter.request.EnableScooterRequestDTO;
import com.appscootercopy.scooterusemicroservice.service.dto.scooter.request.ScooterRequestDTO;
import com.appscootercopy.scooterusemicroservice.service.dto.scooter.request.TripsAndYearRequestDTO;
import com.appscootercopy.scooterusemicroservice.service.dto.scooter.response.*;
import com.appscootercopy.scooterusemicroservice.service.dto.scooterStop.request.ScooterStopRequestDTO;
import com.appscootercopy.scooterusemicroservice.service.dto.scooterStop.response.ScooterStopResponseDTO;
import com.appscootercopy.scooterusemicroservice.service.dto.trip.ScooterByTripsYearResponseDTO;
import com.appscootercopy.scooterusemicroservice.service.dto.ubication.request.UbicationRequestDTO;
import com.appscootercopy.scooterusemicroservice.service.dto.ubication.response.UbicationResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/scooters")
public class ScooterController {

    private ScooterService scooterService;

    public ScooterController(ScooterService scooterService) {
        this.scooterService = scooterService;
    }

    @GetMapping("/{licensePlate}")
    public ScooterResponseDTO getScooterByLicensePlate(@PathVariable String licensePlate){
        //cualquira
        return scooterService.findScooterByLicensePlate(licensePlate);
    }

    @GetMapping("/")
    public List<ScooterResponseDTO> getAllScooter(){
        //cualquiera
        return this.scooterService.findAllScooter();
    }

    @GetMapping("/availability")
    public ReportAvailabilityDTO getCountScooterByAvailability(){
        //manager
        return this.scooterService.findCountScooterByAvailability();
    }

    @GetMapping("/close")
    public List<ScooterResponseDTO> getAllScooterCloseToMe(@RequestBody @Valid UbicationRequestDTO request){
        //cualquiera
        return this.scooterService.findAllScooterCloseToMe(request);
    }

    @PostMapping("")
    public ResponseEntity saveScooter(@RequestBody @Valid ScooterRequestDTO request){
        //admin
        return scooterService.saveScooter(request);
    }

    @DeleteMapping("/{id}")
    public void deleteScooter(@PathVariable Long id){
        this.scooterService.deleteScooter(id);
        //admin
    }

    @PutMapping("/{id}")
    public ResponseEntity updateScooter(@RequestBody @Valid ScooterRequestDTO request, @PathVariable Long id){
        //admin
        return this.scooterService.updateScooter(request, id);
    }

    @PatchMapping("/{id}")
    public ScooterResponseDTO changeAvailabilityScooter(@RequestBody @Valid EnableScooterRequestDTO request, @PathVariable Long id) {
        //admin y manager
        return this.scooterService.enableScooter(request, id);
    }

    @GetMapping("/report/kms")
    public List<ReportScootersDTO> getReportUseScootersByKms() {
        //manager
        return this.scooterService.findUseScootersByKms();
    }

    @GetMapping("/report/pauses")
    public List<ReportScootersDTO> getReportUseScootersByTimeCcPauses() {
        //manager
        return this.scooterService.findUseScootersByTimeCcPauses();
    }

    @GetMapping("/report/non&pauses")
    public List<ReportScootersDTO> getReportUseScootersByTimeOutPauses() {
        //manager
        return this.scooterService.findUseScootersByTimeOutPauses();
    }

    @GetMapping("/stops/{ubicationId}")
    public ScooterStopResponseDTO getScooterStopByUbication(@PathVariable Long ubicationId){
        return scooterService.findScooterStopByUbication(ubicationId);
    }

    @GetMapping("/trips&year")
    public List<ScooterByTripsYearResponseDTO> getAllScooterByTripsAndYear(@RequestBody @Valid TripsAndYearRequestDTO request){
        //admin
        return scooterService.findAllScooterByTripsAndYear(request);
    }

    @GetMapping("/stops/")
    public List<ScooterStopResponseDTO> getAllScooterStop(){
        //cualquiera
        return this.scooterService.findAllScooterStop();
    }

    @PostMapping("/stops")
    public ResponseEntity saveScooterStop(@RequestBody @Valid ScooterStopRequestDTO request) {
        //manager y admin
        return this.scooterService.saveScooterStop(request);
    }

    @DeleteMapping("/stops/{id}")
    public void deleteScooterStop(@PathVariable Long id){
        //manager y admin
        this.scooterService.deleteScooterStop(id);
    }

    @PutMapping("/stops/{id}")
    public ResponseEntity updateScooterStop(@RequestBody @Valid ScooterStopRequestDTO request, @PathVariable Long id){
        //manager y admin
        return this.scooterService.updateScooterStop(request, id);
    }

    @GetMapping("/ubications/{id}")
    public UbicationResponseDTO getUbicationById(@PathVariable Long id){
        //cualquiera
        return scooterService.findUbicationById(id);
    }

    @GetMapping("/ubications/")
    public List<UbicationResponseDTO> getAllUbication(){
        //cualquiera
        return this.scooterService.findAllUbication();
    }


}
