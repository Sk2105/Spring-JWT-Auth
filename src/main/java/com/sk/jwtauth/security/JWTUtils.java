package com.sk.jwtauth.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JWTUtils {

    private String secret = "secret";

    public String generateToken(String email) {
        return JWT.create()
                .withSubject("User Details")
                .withClaim("email", email)
                .withIssuedAt(new Date())
                .withIssuer("Spring Boot JWT")
                .sign(Algorithm.HMAC256(secret));

    }

    public String validateToken(String token) throws Exception {
        try {
            return JWT.require(Algorithm.HMAC256(secret))
                    .build()
                    .verify(token)
                    .getClaim("email")
                    .asString();
        } catch (Exception e) {
            throw new Exception("Invalid token " + e.getMessage());
        }
    }
}
