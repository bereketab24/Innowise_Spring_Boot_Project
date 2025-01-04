package com.example.SimpleCRUDAPI.util;

import java.util.Date;
// To use the JWT library, we need to import the io.jsonwebtoken package. we use the JwtBuilder class to create a JWT token and the Jwts class to parse and validate the token.
import io.jsonwebtoken.*;
// import the Keys class from the io.jsonwebtoken.security package to generate the secret key for the JWT token.
import io.jsonwebtoken.security.Keys;
// Import the Component annotation from the org.springframework.stereotype package to mark the class as a Spring component.
import org.springframework.stereotype.Component;
// Import Key from the java.security package to generate the secret key for the JWT token.
import java.security.Key;


@Component
// The JwtUtil class is a utility class that provides methods to generate and validate JWT tokens.
public class JwtUtil {
    // The secret key is used to sign the JWT token. It is generated using the Keys.secretKeyFor method, which generates a new secret key for the specified signature algorithm.
    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // The createToken method generates a JWT token for the specified username and expiration time.
    public String createToken(String username) {
        // The JwtBuilder class is used to create a JWT token. We set the subject of the token to the username and the expiration time to 1 hour from the current time.
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", "user")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(key)
                .compact();
    }

    // The validateToken method validates the JWT token and returns the username if the token is valid.
    public String validateToken(String token) {
        try {
            // The Jwts.parserBuilder method creates a new JwtParser instance to parse and validate the token. We set the signing key to the secret key and parse the token to extract the subject (username).
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException e) {
            System.out.println("Invalid or expired token " + e.getMessage());
            // If the token is invalid or expired, a JwtException is thrown. In this case, we return null.
            return null;
        }
    }
    
}
