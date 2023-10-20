package com.ScootersApp.controller;

import com.ScootersApp.Service.DTOs.User.request.UserLoginRequest;
import com.ScootersApp.Service.DTOs.User.request.UserRequest;
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

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDTO> getAllUsers(){
        return this.service.findAll();
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDTO createUser(@RequestBody UserRequest user) throws Exception {
        return  this.service.save(user);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> login(@RequestBody UserLoginRequest user){
        UserLoginResponseDTO userRequest = this.service.findByMailAndPassword(user.getMail(), user.getPassword());
        return new ResponseEntity(userRequest, HttpStatus.OK);
    }

    @GetMapping("/{mail}")
    public UserLoginResponseDTO getByMail(@PathVariable String mail){
        return this.service.findMyMail(mail);
    }
}
