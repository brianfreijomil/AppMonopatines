package com.scooter.apiGateway.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping("/hello")
    public ResponseEntity hello(){
        return new ResponseEntity("Hello Admin", HttpStatus.OK);
    }
}
