package com.scooter.apiGateway.controller;

import com.scooter.apiGateway.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    public ResponseEntity login(String email, String password) {
        return this.service.login(email, password);
    }
}
