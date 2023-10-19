package com.ScootersApp.controller;

import com.ScootersApp.Service.DTOs.User.response.UserLoginResponseDTO;
import com.ScootersApp.Service.DTOs.User.response.UserResponseDTO;
import com.ScootersApp.Service.UserService;
import com.ScootersApp.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService service;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDTO> getAllUsers(){
        return this.service.findAll();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public void createUser(@RequestBody User user){
        this.service.save(user);
    }

    @PostMapping("/")
    public ResponseEntity<UserLoginResponseDTO> login(@RequestBody User user){
        UserLoginResponseDTO userRequest = this.service.findByMail(user.getMail());
        return new ResponseEntity(userRequest, HttpStatus.OK);
    }
}
