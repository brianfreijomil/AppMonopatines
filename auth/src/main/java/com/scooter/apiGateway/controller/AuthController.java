package com.scooter.apiGateway.controller;

import com.scooter.apiGateway.DTO.*;
import com.scooter.apiGateway.repository.UserRepository;
import com.scooter.apiGateway.utils.JWTUtill;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JWTUtill jwtUtill;

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    public AuthController(JWTUtill jwtUtill, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.jwtUtill = jwtUtill;
        this.passwordEncoder = new BCryptPasswordEncoder(16);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid UserRequestDTO user){
        UserDto userDto = this.userRepository.getUser(user.getMail());
        if(userDto!= null && passwordEncoder.matches(user.getPassword(), userDto.getPassword())){
            String token = this.jwtUtill.createToken(userDto.getEmail(), userDto.getRoles());
            return new ResponseEntity(token, HttpStatus.OK);
        }
        return new ResponseEntity("invalid user", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/valid")
    public ResponseEntity validToken(@RequestBody String token){
        if(this.jwtUtill.isValid(token)){

        }
        return new ResponseEntity("Token invalid", HttpStatus.UNAUTHORIZED);
    }

}
