package com.fssa.todo.jwtutil;


import io.jsonwebtoken.Claims;
<<<<<<< HEAD
=======
import io.jsonwebtoken.Jwt;
>>>>>>> d6f374cc3024ef6050749c8b4c81bbc13c2eaf7f
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class jwtService {

    private String secretKey;

    // field is private so constructor created and set the secretKey
    public jwtService() {
        secretKey = generateKey();
    }

    // Create a new default method for create a Token
    public String generateKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey secretKey = keyGen.generateKey();
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("Error Generating key", ex);
        }
    }


    public String generateToken(String email) {

        // claims means refer the payload it means users data
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
<<<<<<< HEAD
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 3 minutes
                .signWith(getKey(), SignatureAlgorithm.HS256).compact();
=======
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 3)) // 3 minutes
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
>>>>>>> d6f374cc3024ef6050749c8b4c81bbc13c2eaf7f

    }

    // Create a new method in key that is inbuild security in spring
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);

    }


    // This method is for extract the useremail from the email
    public String extractEmail(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
<<<<<<< HEAD
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
=======
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJwt(token).getBody();
>>>>>>> d6f374cc3024ef6050749c8b4c81bbc13c2eaf7f
    }

    // This method is for validate the token
    public boolean validateToken(String token, UserDetails userDetails) {
        final String email = extractEmail(token);
<<<<<<< HEAD
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
=======
        return (email.equals(userDetails.getUsername()) && !isTokenExpried(token));
    }

    private boolean isTokenExpried(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
>>>>>>> d6f374cc3024ef6050749c8b4c81bbc13c2eaf7f
        return extractClaim(token, Claims::getExpiration);
    }
}
