package com.scooter.apiGateway.controller;

import com.scooter.apiGateway.service.Constants;
import com.scooter.apiGateway.service.ScooterService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/ubication")
}
