package com.scooter.apiGateway;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.scooter.apiGateway.service.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AuthJWT {

private static final String SECRET_KEY = "scooterApp";
private static final String ISSUER = "scooterApp";
private static final Long TIMER_OF_EXPIRATION = 5000L;
private final Algorithm algorithm;
private final JWTVerifier verifier;
public AuthJWT(){
    this.algorithm = Algorithm.HMAC256(SECRET_KEY);
    this.verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
}

    public String createJWT(String userEmail, String password) {
        //me llega un usuario y contraseña
        //---- voy a buscar al usuario, hago fetch a mi micro
        User user = new User();
        if(user != null){
            String token = JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(userEmail)
                    .withClaim("roles", user.getRoles())
                    .withIssuedAt(new Date(System.currentTimeMillis()))
                    .withExpiresAt(new Date(System.currentTimeMillis()+ TIMER_OF_EXPIRATION))
                    .sign(algorithm);
            return token;
        }
        //lanzar exception q el usuario no existe o contraseña incorrecta
        return null;
    }

    public static DecodedJWT decodedJWT(String jwtToken) {
        try {
            DecodedJWT decodedJWT = JWT.decode(jwtToken);
            return decodedJWT;
        } catch (JWTDecodeException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static boolean isJWTExpired(DecodedJWT decodedJWT) {
        Date expiresAt = decodedJWT.getExpiresAt();
        return expiresAt.before(new Date());
    }
}
