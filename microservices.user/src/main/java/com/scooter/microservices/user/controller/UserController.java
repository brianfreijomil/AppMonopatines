package com.scooter.microservices.user.controller;

import com.scooter.microservices.user.domain.User;
import com.scooter.microservices.user.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("user")
public class UserController {

    private UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/")
    public ResponseEntity login(@RequestBody User userRequeset){
        User user = this.repository.findByEmailAndPassword(userRequeset.getEmail(), userRequeset.getPassword());
        return new ResponseEntity(user, HttpStatus.OK);
    }
}
