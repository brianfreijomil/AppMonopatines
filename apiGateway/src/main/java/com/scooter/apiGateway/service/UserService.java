package com.scooter.apiGateway.service;


import com.scooter.apiGateway.AuthJWT;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private AuthJWT jwt;
    public UserService(AuthJWT jwt) {
        this.jwt = jwt;
    }

    public ResponseEntity login(String email, String password){
        WebClient webClient = WebClient.create("urlusuario");
        User user = new User(email, password);
        Mono entityMono = webClient.post()
                .bodyValue(user)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> {
                    if(response.statusCode().equals(HttpStatus.OK)){
                        return response.bodyToMono(User.class);
                    }else{
                        return response.createError();
                    }
                });
        String token = this.jwt.createJWT(email, password);
        //devolver el token con el usuario ? o solo
        return new ResponseEntity(entityMono, HttpStatus.OK);
    }
}
