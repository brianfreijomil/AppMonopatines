package com.scooter.apiGateway.service;

import com.scooter.apiGateway.DTO.DisableDTO;
import com.scooter.apiGateway.DTO.UserRequestCreateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UsersService {
    private static final String URL = "http://localhost:8081";

    @Autowired
    private WebClient.Builder webClient;
    private PasswordEncoder passwordEncoder;

    public UsersService() {
        this.passwordEncoder = new BCryptPasswordEncoder(16);
    }

    public ResponseEntity disableUsers(String email, DisableDTO value){
        ResponseEntity res = this.webClient.build()
                .put()
                .uri("http://localhost:8081/api/users/{email}/disable", email)
                .bodyValue(value)
                .retrieve()
                .toEntity(String.class)
                .block();

        return res;
    }

    public ResponseEntity createUsers(UserRequestCreateDTO user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
       ResponseEntity res = webClient.build()
                .post()
                .uri(URL + "/api/users")
                .bodyValue(user)
                .retrieve()
                .toEntity(String.class)
                .block();
        return res;
    }
}
