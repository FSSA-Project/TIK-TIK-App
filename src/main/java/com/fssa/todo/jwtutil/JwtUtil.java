package com.fssa.todo.jwtutil;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;


@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(String username) {
        String encodedSecret = Base64.getEncoder().encodeToString(secret.getBytes());

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(SignatureAlgorithm.HS512, encodedSecret)
                .compact();
    }

    /**
     * Below the code for verify the token
     *
     * @param authorization
     * @throws Exception
     */
    public void verify(String authorization) throws Exception {
        try {
            // Remove the Bearer prefix from the token
            if (authorization.startsWith("Bearer ")) {
                authorization = authorization.substring(7);
            }

            // Parse the token
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(authorization)
                    .getBody();

        } catch (SignatureException e) {
            throw new Exception("Invalid JWT signature", e);
        } catch (Exception e) {
            throw new Exception("JWT token verification failed", e);
        }
    }
}

