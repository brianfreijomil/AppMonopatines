package com.scooter.apiGateway.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JWTUtill {
    private static final String SECRET_KEY = "APP_MONOPATIN";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);
    private static final Long TIMER_OF_EXPIRATION = TimeUnit.MINUTES.toMillis(5);

    public String createToken(String username){
        return JWT.create()
                .withSubject(username)
                .withIssuer("App_Monopatin")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TIMER_OF_EXPIRATION))
                .sign(ALGORITHM);
    }
}
